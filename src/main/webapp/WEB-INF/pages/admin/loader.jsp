<!-- Loading Spinner (hidden by default) -->
<div id="loadingSpinner" style="display:none; position: fixed; top:0; left:0; width:100%; height:100%; background: rgba(255,255,255,0.7); z-index: 9999; text-align:center;">
    <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">
        <div class="spinner"></div>
        <p>Loading, please wait...</p>
    </div>
</div>

<style>
    .spinner {
        border: 8px solid #f3f3f3;
        border-top: 8px solid #3498db;
        border-radius: 50%;
        width: 60px;
        height: 60px;
        animation: spin 1s linear infinite;
    }
    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }
</style>

<script>
    // Show spinner when form submits or page is navigating
    document.addEventListener("DOMContentLoaded", function() {
        // Show spinner on any form submit
        document.querySelectorAll("form").forEach(function(form) {
            form.addEventListener("submit", function() {
                document.getElementById("loadingSpinner").style.display = "block";
            });
        });

        // Show spinner on any link click that navigates away (optional)
        document.querySelectorAll("a").forEach(function(anchor) {
            anchor.addEventListener("click", function(e) {
                const href = anchor.getAttribute("href");
                // Only show spinner for normal page navigation, ignore anchors/JS links
                if (href && !href.startsWith("#") && !href.startsWith("javascript:")) {
                    document.getElementById("loadingSpinner").style.display = "block";
                }
            });
        });
    });
</script>
