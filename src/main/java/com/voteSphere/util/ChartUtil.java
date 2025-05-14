package com.voteSphere.util;

import com.voteSphere.dao.ElectionDao;
import com.voteSphere.dao.VoteDao;
import com.voteSphere.model.User;
import com.voteSphere.model.Vote;
import com.voteSphere.service.ElectionService;
import com.voteSphere.service.VoteService;
import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class ChartUtil {

    private static final int PIE_MAX_VISIBLE_PARTIES = 5;


    private static final Color[] PIE_FIXED_COLORS = new Color[] {
            new Color(0x1f77b4), // Blue
            new Color(0xff7f0e), // Orange
            new Color(0x2ca02c), // Green
            new Color(0xd62728), // Red
            new Color(0x9467bd), // Purple
            new Color(0x8c564b) // Brown
//            new Color(0xe377c2)  // Pink
    };

    public static PieChart createElectionVoteStatsPieChart(int electionId) {
        String electionName = ElectionService.getElectionById(electionId).getName();
        PieChart chart = new PieChartBuilder()
                .width(1200)
                .height(800)
                .title(electionName + " - Vote Distribution")
                .build();

        Map<String, Integer> partyVotes = VoteService.getAllPartyNamesAndRespectiveVoteCountInElection(electionId);
        int totalVotes = VoteDao.getTotalVotesInElection(electionId);

        if (totalVotes == 0 || partyVotes.isEmpty()) {
            chart.addSeries("No Votes", 100.0);
            return chart;
        }

        // Sort by vote count descending
        List<Entry<String, Integer>> sortedParties = new ArrayList<>(partyVotes.entrySet());
        sortedParties.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        double othersTotal = 0.0;

        for (int i = 0; i < sortedParties.size(); i++) {
            String party = sortedParties.get(i).getKey();
            int votes = sortedParties.get(i).getValue();
            double percentage = (double) votes / totalVotes * 100;

            if (i < PIE_MAX_VISIBLE_PARTIES) {
                chart.addSeries(party + " (" + String.format("%.1f", percentage) + "%)", percentage);
            } else {
                othersTotal += percentage;
            }
        }

        if (othersTotal > 0) {
            chart.addSeries("Others (" + String.format("%.1f", othersTotal) + "%)", othersTotal);
        }

        // Use fixed color array
        chart.getStyler().setSeriesColors(PIE_FIXED_COLORS);

        // Styling
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setCircular(true);
        chart.getStyler().setStartAngleInDegrees(90);
        chart.getStyler().setChartTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        chart.getStyler().setLabelsFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        chart.getStyler().setLabelsFontColor(Color.WHITE);
        chart.getStyler().setLegendPadding(15);
        chart.getStyler().setPlotContentSize(0.7);

        return chart;
    }




    public static CategoryChart createElectionVotersAgeBarChart(int electionId) {
        List<Vote> votes = VoteDao.findVoteHistoryForElection(electionId);

        // Define age ranges
        TreeMap<String, Integer> ageRangeToCount = new TreeMap<>();
        int[][] ageRanges = {
                {16, 21},
                {21, 26},
                {26, 31},
                {31, 36},
                {36, 41},
                {41, 46},
                {46, 51},
                {51, 56},
                {56, 61},
                {61, 66},
                {66, 75},
                {75, 100}
        };

        for (int[] range : ageRanges) {
            String label = range[0] + "-" + range[1];
            ageRangeToCount.put(label, 0);
        }

        // Group by age range
        for (Vote vote : votes) {
            int age = vote.getVoter().getAge(); // assuming getVoter() gives Voter and getAge() gives int
            for (int[] range : ageRanges) {
                if (age >= range[0] && age <= range[1]) {
                    String label = range[0] + "-" + range[1];
                    ageRangeToCount.put(label, ageRangeToCount.get(label) + 1);
                    break;
                }
            }
        }

        // Prepare data
        List<String> ageRangeLabels = new ArrayList<>(ageRangeToCount.keySet());
        List<Integer> voteCounts = new ArrayList<>(ageRangeToCount.values());

        // Create chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(1200)
                .height(800)
                .title("Votes by Age Group")
                .xAxisTitle("Age Range")
                .yAxisTitle("Number of Votes")
                .build();

        chart.getStyler().setChartTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setPlotGridLinesVisible(true);
        chart.getStyler().setAvailableSpaceFill(0.9);

        chart.addSeries("Vote Count", ageRangeLabels, voteCounts);

        return chart;
    }



    public static PieChart createElectionGenderDonutChart(int electionId) {

        // Get vote data
        List<Vote> votes = VoteService.getVotesByElectionId(electionId);

        // Count gender participation
        Map<String, Integer> genderCounts = new LinkedHashMap<>(); // Preserves insertion order
        genderCounts.put("Male", 0);
        genderCounts.put("Female", 0);
        genderCounts.put("Other", 0);


        for (Vote vote : votes) {
            User voter = vote.getVoter();
            String gender = (voter == null || voter.getGender() == null || voter.getGender().trim().isEmpty())
                    ? "Other"
                    : ValidationUtil.capitalize(voter.getGender().trim());

            genderCounts.put(genderCounts.containsKey(gender) ? gender : "Other",
                    genderCounts.get(gender) + 1);
        }

        // Filter out genders with zero participation
        genderCounts.values().removeIf(count -> count == 0);

        // Create chart
        PieChart chart = new PieChartBuilder()
                .width(1000)
                .height(800)
                .title("Gender Participation in "+ ElectionDao.getElectionNameById(electionId))
                .theme(Styler.ChartTheme.GGPlot2)
                .build();


        // Custom styling
        PieStyler styler = chart.getStyler();

        // Font styling
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 28);
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        Font legendFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);

        styler.setChartTitleFont(titleFont);
        styler.setLegendFont(legendFont);
        styler.setLabelsFont(labelFont);
        styler.setSumFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));


        // Color scheme
        Color[] genderColors = {
                new Color(65, 105, 225),   // Royal Blue (Male)
                new Color(220, 20, 60),    // Crimson (Female)
                new Color(46, 139, 87)     // Sea Green (Other)
        };
        styler.setSeriesColors(genderColors);

        // Donut configuration
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        styler.setDonutThickness(0.35); // 35% of radius

        // Label configuration
        styler.setLabelsVisible(true);
        styler.setLabelType(PieStyler.LabelType.NameAndPercentage);
        styler.setLabelsDistance(0.85);


        // Legend configuration
        styler.setLegendVisible(true);
        styler.setLegendPosition(Styler.LegendPosition.OutsideE);
        styler.setLegendLayout(Styler.LegendLayout.Vertical);
        styler.setLegendPadding(15);

        // Background and decoration
        styler.setPlotBackgroundColor(new Color(245, 245, 245));
        styler.setChartBackgroundColor(Color.WHITE);
        styler.setPlotBorderVisible(false);
        styler.setChartPadding(20);


        // Add data with custom tooltips
        genderCounts.forEach((gender, count) -> {
            String tooltip = String.format("%s: %,d votes (%.1f%%)",
                    gender,
                    count,
                    (count * 100.0 / votes.size()));

            chart.addSeries(gender, count);
        });

        return chart;
    }
}
