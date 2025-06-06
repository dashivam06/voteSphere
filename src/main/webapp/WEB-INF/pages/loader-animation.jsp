<!-- Loading Spinner Overlay -->
<div id="loadingSpinner" role="alert" aria-live="assertive" aria-busy="true" style="display:none;">
    <div class="spinner-container" aria-label="Loading content, please wait">
        <svg class="spinner" viewBox="0 0 50 50" aria-hidden="true">
            <circle class="path" cx="25" cy="25" r="20" fill="none" stroke-width="5"></circle>
        </svg>
        <p class="loading-text">Loading, please wait...</p>
    </div>
</div>

<style>
    /* Overlay covers entire viewport with subtle blurred backdrop */
    #loadingSpinner {
        position: fixed;
        inset: 0;
        background: rgba(255 255 255 / 0.75);
        backdrop-filter: blur(4px);
        z-index: 9999;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .spinner-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
        color: #3498db;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        user-select: none;
    }
    /* SVG spinner with smooth stroke animation */
    .spinner {
        width: 80px;
        height: 80px;
        animation: rotate 2s linear infinite;
    }

    .path {
        stroke: #3498db;
        stroke-linecap: round;
        animation: dash 1.5s ease-in-out infinite;
    }

    @keyframes rotate {
        100% {
            transform: rotate(360deg);
        }
    }

    @keyframes dash {
        0% {
            stroke-dasharray: 1, 150;
            stroke-dashoffset: 0;
        }
        50% {
            stroke-dasharray: 90, 150;
            stroke-dashoffset: -35;
        }
        100% {
            stroke-dasharray: 90, 150;
            stroke-dashoffset: -124;
        }
    }

    .loading-text {
        margin-top: 1rem;
        font-size: 1.2rem;
        font-weight: 600;
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const spinner = document.getElementById("loadingSpinner");
        const forms = document.querySelectorAll("form");
        const links = document.querySelectorAll("a[href]:not([href^='#']):not([href^='javascript:'])");

        // Show spinner on form submit
        forms.forEach(form => {
            form.addEventListener("submit", () => {
                spinner.style.display = "flex";
            });
        });

        // Debounce clicks on links to prevent flicker if multiple clicks
        let linkTimeout;
        links.forEach(link => {
            link.addEventListener("click", (e) => {
                clearTimeout(linkTimeout);
                linkTimeout = setTimeout(() => {
                    spinner.style.display = "flex";
                }, 50);
            });
        });
    });
</script>
