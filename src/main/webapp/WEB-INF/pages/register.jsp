<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css" />
    <link
      href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
      rel="stylesheet"
    />
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }

      /* Base styles */
      body {
        font-family: "Inter", sans-serif;
        color: #333;
        background-color: #f9fafb;
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        margin: 0;
      }

      .container {
        width: 100%;
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 15px;
      }

      /* Header styles */
      header {
        background-color: #fff;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        position: sticky;
        top: 0;
        z-index: 10;
        animation: fadeIn 0.6s ease-in-out;
      }

      .header-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 64px;
      }

      .logo {
        display: flex;
        align-items: center;
        text-decoration: none;
      }

      .logo svg {
        height: 32px;
        width: 32px;
        color: #0284c7;
        transition: transform 0.3s;
      }

      .logo:hover svg {
        transform: rotate(12deg);
      }

      .logo span {
        margin-left: 8px;
        font-size: 1.25rem;
        font-weight: 700;
        color: #0284c7;
        transition: all 0.3s;
      }

      .logo:hover span {
        color: #0369a1;
      }

      nav {
        display: none;
      }

      @media (min-width: 768px) {
        nav {
          display: flex;
          gap: 32px;
        }
      }

      nav a {
        color: #4b5563;
        padding: 8px 12px;
        font-size: 0.875rem;
        font-weight: 500;
        text-decoration: none;
        transition: all 0.3s;
      }

      nav a:hover {
        color: #0284c7;
        transform: scale(1.05);
      }

      .auth-buttons {
        display: flex;
        align-items: center;
      }

      .login-btn {
        display: none;
      }

      @media (min-width: 768px) {
        .login-btn {
          display: inline-flex;
          align-items: center;
          justify-content: center;
          padding: 8px 16px;
          border: none;
          font-size: 0.875rem;
          font-weight: 500;
          border-radius: 6px;
          color: #fff;
          background-color: #0284c7;
          transition: all 0.3s;
          text-decoration: none;
        }

        .login-btn:hover {
          background-color: #0369a1;
          box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
          transform: translateY(-2px);
        }
      }

      .register-btn {
        margin-left: 16px;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        padding: 8px 16px;
        border: 1px solid #0284c7;
        font-size: 0.875rem;
        font-weight: 500;
        border-radius: 6px;
        color: #0284c7;
        background-color: #fff;
        transition: all 0.3s;
        text-decoration: none;
      }

      .register-btn:hover {
        background-color: #f9fafb;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        transform: translateY(-2px);
      }

      .menu-btn {
        background-color: #fff;
        padding: 8px;
        border-radius: 6px;
        color: #9ca3af;
        border: none;
        margin-left: 16px;
        cursor: pointer;
        transition: all 0.3s;
      }

      @media (min-width: 768px) {
        .menu-btn {
          display: none;
        }
      }

      .menu-btn:hover {
        color: #4b5563;
        background-color: #f3f4f6;
      }

      /* Main content */
      .main-content {
        flex-grow: 1;
        padding: 40px 15px;
      }

      .form-container {
        max-width: 768px;
        margin: 0 auto;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.08);
        padding: 24px;
        animation: slideUp 0.6s ease-out;
      }

      .form-title {
        font-size: 1.5rem;
        font-weight: 700;
        text-align: center;
        color: #1f2937;
        margin-bottom: 24px;
      }

      /* Progress bar */
      .progress-container {
        margin-bottom: 32px;
      }

      .progress-info {
        display: flex;
        justify-content: space-between;
        margin-bottom: 8px;
      }

      .step-indicator,
      .progress-percentage {
        font-size: 0.875rem;
        font-weight: 500;
      }

      .progress-bar-container {
        width: 100%;
        background-color: #e5e7eb;
        border-radius: 9999px;
        height: 10px;
      }

      .progress-bar {
        background-color: #0369a1;
        height: 10px;
        border-radius: 9999px;
        transition: width 0.5s ease-in-out;
        width: 25%;
      }

      /* Form steps */
      .form-step {
        display: none;
        opacity: 0;
        transform: translateX(100px);
        transition: all 0.5s ease-in-out;
      }

      .form-step.active {
        display: block;
        opacity: 1;
        transform: translateX(0);
      }

      .form-step.slide-left {
        animation: slideLeft 0.5s forwards;
      }

      .form-step.slide-right {
        animation: slideRight 0.5s forwards;
      }

      function

      validateTerms
      (
      )
      {
        const terms = document . getElementById('terms')
      ;
        const validationSummary = document . getElementById('validation-summary-step4')
      ;
        const validationList = document . getElementById('validation-list-step4')
      ;

        if (! terms . checked
      )
      {
      validationList.innerHTML

      =
      '<li>You must agree to the Terms and Conditions</li>'
      ;
      validationSummary.classList.

      add
      (
      'visible'
      )
      ;
      }
      else {
        validationSummary.classList.

      remove('visible');
      }

      }

      @keyframes slideLeft {
        0% {
          opacity: 1;
          transform: translateX(0);
        }
        100% {
          opacity: 0;
          transform: translateX(-100px);
        }
      }

      @keyframes slideRight {
        0% {
          opacity: 1;
          transform: translateX(0);
        }
        100% {
          opacity: 0;
          transform: translateX(100px);
        }
      }

      .slide-in-right {
        animation: slideInRight 0.5s forwards;
      }

      .slide-in-left {
        animation: slideInLeft 0.5s forwards;
      }

      @keyframes slideInRight {
        0% {
          opacity: 0;
          transform: translateX(100px);
        }
        100% {
          opacity: 1;
          transform: translateX(0);
        }
      }

      @keyframes slideInLeft {
        0% {
          opacity: 0;
          transform: translateX(-100px);
        }
        100% {
          opacity: 1;
          transform: translateX(0);
        }
      }

      @keyframes fadeIn {
        0% {
          opacity: 0;
        }
        100% {
          opacity: 1;
        }
      }

      @keyframes slideUp {
        0% {
          transform: translateY(30px);
          opacity: 0;
        }
        100% {
          transform: translateY(0);
          opacity: 1;
        }
      }

      /* Step headings */
      .step-title {
        font-size: 1.25rem;
        font-weight: 600;
        margin-bottom: 16px;
        color: #374151;
      }

      /* Form elements */
      .form-grid {
        display: grid;
        grid-template-columns: 1fr;
        gap: 16px;
        margin-bottom: 16px;
      }

      @media (min-width: 768px) {
        .form-grid {
          grid-template-columns: 1fr 1fr;
        }
      }

      .form-group {
        margin-bottom: 16px;
      }

      .form-label {
        display: block;
        font-size: 0.875rem;
        font-weight: 500;
        color: #374151;
        margin-bottom: 4px;
      }

      .form-input,
      .form-textarea {
        width: 100%;
        padding: 8px 12px;
        border: 1px solid #d1d5db;
        border-radius: 6px;
        font-family: "Inter", sans-serif;
        font-size: 1rem;
      }

      .form-input:focus,
      .form-textarea:focus {
        outline: none;
        border-color: #0284c7;
        box-shadow: 0 0 0 2px rgba(2, 132, 199, 0.2);
      }

      .form-textarea {
        resize: vertical;
        min-height: 80px;
      }

      .form-hint {
        font-size: 0.75rem;
        color: #6b7280;
        margin-top: 4px;
      }

      .form-check {
        display: flex;
        align-items: flex-start;
        margin-bottom: 24px;
      }

      .form-checkbox {
        height: 16px;
        width: 16px;
        color: #0284c7;
        border: 1px solid #d1d5db;
        border-radius: 4px;
        margin-top: 2px;
      }

      .form-check-label {
        margin-left: 8px;
        font-size: 0.875rem;
        color: #374151;
      }

      .form-check-label a {
        color: #0284c7;
        text-decoration: none;
      }

      .form-check-label a:hover {
        text-decoration: underline;
      }

      /* Buttons */
      .form-buttons {
        display: flex;
        justify-content: space-between;
      }

      .btn {
        padding: 8px 16px;
        border-radius: 6px;
        font-weight: 500;
        font-size: 0.875rem;
        cursor: pointer;
        transition: all 0.3s;
        border: none;
      }

      .btn:focus {
        outline: none;
      }

      .btn-primary {
        background-color: #0369a1;
        color: white;
      }

      .btn-primary:hover {
        background-color: #075985;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        transform: scale(1.05);
      }

      .btn-secondary {
        background-color: #d1d5db;
        color: #374151;
      }

      .btn-secondary:hover {
        background-color: #9ca3af;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        transform: scale(1.05);
      }

      /* Footer */
      footer {
        background-color: #fff;
        border-top: 1px solid #e5e7eb;
        padding: 32px 0;
        margin-top: auto;
      }

      .footer-content {
        text-align: center;
        color: #6b7280;
        font-size: 0.875rem;
      }

      .footer-content p {
        margin: 0;
      }

      .footer-content p + p {
        margin-top: 8px;
      }

      /* Form validation */
      .border-red {
        border-color: #ef4444 !important;
      }

      .radio-group {
        display: flex;
        align-items: center;
        gap: 25px;
      }

      .radio-option {
        display: flex;
        align-items: center;
        gap: 8px;
      }

      .image-preview {
        max-width: 100px;
        height: auto;
        margin-top: 8px;
        padding: 2px;
      }

      /* Error message styles */
      .error-message {
        color: #dc2626;
        font-size: 0.875rem;
        margin-top: 4px;
        display: none;
        padding: 8px;
        border-radius: 4px;
        background-color: #fee2e2;
        border: 1px solid #fecaca;
        white-space: pre-line;
      }

      .error-message.visible {
        display: block;
        animation: fadeIn 0.3s ease-in-out;
      }

      .form-input.error,
      .form-textarea.error {
        border-color: #dc2626;
      }

      .validation-summary {
        background-color: #fee2e2;
        border: 1px solid #ef4444;
        border-radius: 6px;
        padding: 12px;
        margin-bottom: 16px;
        margin-top: 16px;
        color: #b91c1c;
        display: none;
      }

      .validation-summary.visible {
        display: block;
        animation: fadeIn 0.3s ease-in-out;
      }

      .validation-summary ul {
        margin: 8px 0 0 20px;
      }

      .validation-summary li {
        margin-bottom: 4px;
      }
    </style>
  </head>
  <body>
    <!-- Header/Navigation -->
    <header>
      <div class="container">
        <div class="header-container">
          <div class="logo">
            <a href="/" class="logo">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"
                />
              </svg>
              <span>VoteSphere</span>
            </a>
          </div>
          <nav>
            <a href="/#features">Features</a>
            <a href="/#how-it-works">How It Works</a>
            <a href="/#faqs">FAQs</a>
            <a href="/#contact">Contact</a>
          </nav>
          <div class="auth-buttons">
            <a href="/login" class="login-btn">Log in</a>
            <a href="/register" class="register-btn">Register</a>
            <button type="button" class="menu-btn">
              <svg
                class="h-6 w-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                aria-hidden="true"
                width="24"
                height="24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <div class="main-content">
      <div class="container">
        <div class="form-container">
          <h1 class="form-title">Register as a Voter</h1>

          <!-- Progress Bar -->
          <div class="progress-container">
            <div class="progress-info">
              <span class="step-indicator" id="step-indicator"
                >Step 1 of 4</span
              >
              <span class="progress-percentage" id="progress-percentage"
                >25%</span
              >
            </div>
            <div class="progress-bar-container">
              <div class="progress-bar" id="progress-bar"></div>
            </div>
          </div>

          <!-- Registration Form -->
          <form
            id="registrationForm"
            action="/register"
            method="post"
            enctype="multipart/form-data"
          >
            <!-- Step 1: Personal Information -->
            <div class="form-step active" id="step1">
              <h2 class="step-title">Personal Information</h2>

              <div class="form-grid">
                <div class="form-group">
                  <label for="first_name" class="form-label">First Name</label>
                  <input
                    type="text"
                    id="first_name"
                    name="first_name"
                    required
                    class="form-input"
                    onfocus="clearValidationMessages(1)"
                    onInput="clearValidationMessages(1)"
                  />
                </div>
                <div class="form-group">
                  <label for="last_name" class="form-label">Last Name</label>
                  <input
                    type="text"
                    id="last_name"
                    name="last_name"
                    required
                    class="form-input"
                    onfocus="clearValidationMessages(1)"
                    onInput="clearValidationMessages(1)"
                  />
                </div>
              </div>

              <div class="form-group">
                <label for="voter_id" class="form-label">Voter ID</label>
                <input
                  type="text"
                  id="voter_id"
                  name="voter_id"
                  required
                  class="form-input"
                  onfocus="clearValidationMessages(1)"
                  onInput="clearValidationMessages(1)"
                />
              </div>

              <div class="form-grid">
                <div class="form-group">
                  <label for="email" class="form-label">Email</label>
                  <input
                    type="email"
                    id="email"
                    name="notification_email"
                    required
                    class="form-input"
                  />
                </div>
                <div class="form-group">
                  <label for="dob" class="form-label">Date of Birth</label>
                  <input
                    type="date"
                    id="dob"
                    name="dob"
                    required
                    class="form-input"
                    max="2007-06-06"
                    min="1915-06-06"
                    onblur="validateFormField('dob')"
                  />
                </div>
              </div>

              <div class="form-grid">
                <div class="form-group">
                  <label for="phone_number" class="form-label"
                    >Phone Number</label
                  >
                  <input
                    type="text"
                    id="phone_number"
                    name="phone_number"
                    required
                    class="form-input"
                  />
                </div>

                <div class="form-group">
                  <label for="gender" class="form-label">Gender</label>
                  <div class="radio-group flex items-center gap-4">
                    <div class="radio-option">
                      <input
                        type="radio"
                        id="gender_male"
                        name="gender"
                        value="male"
                        checked
                        onfocus="clearValidationMessages(1)"
                        onInput="clearValidationMessages(1)"
                      />
                      <label for="gender_male">Male</label>
                    </div>
                    <div class="radio-option">
                      <input
                        type="radio"
                        id="gender_female"
                        name="gender"
                        value="female"
                      />
                      <label for="gender_female">Female</label>
                    </div>
                    <div class="radio-option">
                      <input
                        type="radio"
                        id="gender_other"
                        name="gender"
                        value="other"
                      />
                      <label for="gender_other">Others</label>
                    </div>
                  </div>
                </div>
              </div>

              <div class="form-grid">
                <div class="form-group">
                  <label for="password" class="form-label">Password</label>
                  <input
                    type="password"
                    id="password"
                    name="password"
                    required
                    class="form-input"
                  />
                </div>
                <div class="form-group">
                  <label for="confirm_password" class="form-label"
                    >Confirm Password</label
                  >
                  <input
                          type="password"
                          id="confirm_password"
                          name="confirm_password"
                          required
                          class="form-input"
                          onblur="validatePasswordMatch(this, [])"
                  />
                </div>
              </div>

              <div class="form-buttons">
                <div></div>
                <!-- Empty div for spacing -->
                <button
                  type="button"
                  onclick="nextStep(1, 2)"
                  class="btn btn-primary"
                >
                  Next
                </button>
              </div>

              <!-- Validation Summary -->
              <div class="validation-summary" id="validation-summary-step1">

              </div>
            </div>

            <!-- Step 2: Address Information -->
            <div class="form-step" id="step2">
              <h2 class="step-title">Address Information</h2>

              <div class="form-group">
                <label for="temporary_address" class="form-label"
                  >Temporary Address</label
                >
                <textarea
                  id="temporary_address"
                  name="temporary_address"
                  rows="3"
                  required
                  class="form-textarea"
                ></textarea>
              </div>

              <div class="form-group">
                <label for="permanent_address" class="form-label"
                  >Permanent Address</label
                >
                <textarea
                  id="permanent_address"
                  name="permanent_address"
                  rows="3"
                  required
                  class="form-textarea"
                ></textarea>
              </div>

              <div class="form-buttons">
                <button
                  type="button"
                  onclick="prevStep(2, 1)"
                  class="btn btn-secondary"
                >
                  Previous
                </button>
                <button
                  type="button"
                  onclick="nextStep(2, 3)"
                  class="btn btn-primary"
                >
                  Next
                </button>
              </div>

              <!-- Validation Summary -->
              <div class="validation-summary" id="validation-summary-step2">
              </div>
            </div>

            <!-- Step 3: Profile Image -->
            <div class="form-step" id="step3">
              <h2 class="step-title">Profile Image</h2>

              <div class="form-grid">
                <div class="form-group">
                  <label for="profile_image" class="form-label"
                    >Profile Image</label
                  >
                  <input
                    type="file"
                    id="profile_image"
                    name="profile_image"
                    accept="image/*"
                    required
                    class="form-input"
                    onchange="validateFileField('profile_image', 'Profile Image is required', []); previewImage(this, 'profile_image_preview')"
                  />
                  <p class="form-hint">Upload a clear photo of yourself</p>
                  <div class="image-preview" id="profile_image_preview"></div>
                </div>

                <div class="form-group">
                  <label for="image_holding_citizenship" class="form-label"
                    >Image Holding Citizenship</label
                  >
                  <input
                    type="file"
                    id="image_holding_citizenship"
                    name="image_holding_citizenship"
                    accept="image/*"
                    required
                    class="form-input"
                    onchange="validateFileField('image_holding_citizenship', 'Image Holding Citizenship is required', []); previewImage(this, 'holding_citizenship_preview')"
                  />
                  <p class="form-hint">
                    Upload a photo of yourself holding your citizenship document
                  </p>
                  <div
                    class="image-preview"
                    id="holding_citizenship_preview"
                  ></div>
                </div>
              </div>

              <div class="form-group">
                <label for="thumb_print" class="form-label">Thumb Print</label>
                <input
                  type="file"
                  id="thumb_print"
                  name="thumb_print"
                  accept="image/*"
                  required
                  class="form-input"
                  onchange="validateFileField('thumb_print', 'Thumb Print is required', []); previewImage(this, 'thumb_print_preview')"
                />
                <p class="form-hint">
                  Upload a clear image of your thumb print
                </p>
                <div class="image-preview" id="thumb_print_preview"></div>
              </div>

              <div class="form-buttons">
                <button
                  type="button"
                  onclick="prevStep(3, 2)"
                  class="btn btn-secondary"
                >
                  Previous
                </button>
                <button
                  type="button"
                  onclick="nextStep(3, 4)"
                  class="btn btn-primary"
                >
                  Next
                </button>
              </div>

              <!-- Validation Summary -->
              <div class="validation-summary" id="validation-summary-step3">
                <ul id="validation-list-step3"></ul>

              </div>
            </div>

            <!-- Step 4: Document Verification -->
            <div class="form-step" id="step4">
              <h2 class="step-title">Document Verification</h2>

              <div class="form-group">
                <label for="voter_card_front" class="form-label"
                  >Voter Card (Front)</label
                >
                <input
                  type="file"
                  id="voter_card_front"
                  name="voter_card_front"
                  accept="image/*"
                  required
                  class="form-input"
                  onchange="validateFileField('voter_card_front', 'Voter Card Front is required', []); previewImage(this, 'voter_card_front_preview')"
                />
                <div class="image-preview" id="voter_card_front_preview"></div>
              </div>

              <div class="form-grid">
                <div class="form-group">
                  <label for="citizenship_front" class="form-label"
                    >Citizenship (Front)</label
                  >
                  <input
                    type="file"
                    id="citizenship_front"
                    name="citizenship_front"
                    accept="image/*"
                    required
                    class="form-input"
                    onchange="validateFileField('citizenship_front', 'Citizenship Front is required', []); previewImage(this, 'citizenship_front_preview')"
                  />
                  <div
                    class="image-preview"
                    id="citizenship_front_preview"
                  ></div>
                </div>
                <div class="form-group">
                  <label for="citizenship_back" class="form-label"
                    >Citizenship (Back)</label
                  >
                  <input
                    type="file"
                    id="citizenship_back"
                    name="citizenship_back"
                    accept="image/*"
                    required
                    class="form-input"
                    onchange="validateFileField('citizenship_back', 'Citizenship Back is required', []); previewImage(this, 'citizenship_back_preview')"
                  />
                  <div
                    class="image-preview"
                    id="citizenship_back_preview"
                  ></div>
                </div>
              </div>

              <div class="form-check">
                <input
                  type="checkbox"
                  id="terms"
                  name="terms"
                  required
                  class="form-checkbox"
                  onchange="validateTerms()"
                />
                <label for="terms" class="form-check-label">
                  I agree to the
                  <a href="#">Terms and Conditions</a>
                  and confirm that all information provided is accurate.
                </label>
              </div>

              <div class="form-buttons">
                <button
                  type="button"
                  onclick="prevStep(4, 3)"
                  class="btn btn-secondary"
                >
                  Previous
                </button>
                <button
                  type="submit"
                  class="btn btn-primary"
                  onclick="validateStep(4); return false;"
                >
                  Submit Registration
                </button>
              </div>

              <!-- Validation Summary -->
              <div class="validation-summary" id="validation-summary-step4">
                <ul id="validation-list-step4">
                  <li>You must agree to the Terms and Conditions</li>
                </ul>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <footer>
      <div class="container">
        <div class="footer-content">
          <p>&copy; 2023 VoteSphere. All rights reserved.</p>
          <p>Secure online voting system</p>
        </div>
      </div>
    </footer>

    <script>
      const maxFileSize = 1.5 * 1024 * 1024; // 1.5MB in bytes

      function nextStep(currentStep, nextStep) {
        // Validate current step and files if on step 3
        if (!validateStep(currentStep)) {
          return false;
        }

        // Validate files for step 3
        if (currentStep === 3) {
          const profileValid = validateFileField('profile_image', 'Profile Image is required', []);
          const citizenshipValid = validateFileField('image_holding_citizenship', 'Image Holding Citizenship is required', []);
          const thumbprintValid = validateFileField('thumb_print', 'Thumb Print is required', []);

          if (!profileValid || !citizenshipValid || !thumbprintValid) {
            return false;
          }
        }

        // Apply slide-out animation to current step
        const currentStepEl = document.getElementById(`step${currentStep}`);
        currentStepEl.classList.add("slide-left");

        // Wait for animation to complete before showing next step
        setTimeout(() => {
          currentStepEl.classList.remove("active", "slide-left");

          // Show next step with slide-in animation
          const nextStepEl = document.getElementById(`step${nextStep}`);
          nextStepEl.classList.add("active", "slide-in-right");

          // Remove animation class after it completes
          setTimeout(() => {
            nextStepEl.classList.remove("slide-in-right");
          }, 500);

          // Update progress bar with animation
          const progressPercentage = (nextStep / 4) * 100;
          document.getElementById(
            "progress-bar"
          ).style.width = `${progressPercentage}%`;
          document.getElementById(
            "progress-percentage"
          ).textContent = `${progressPercentage}%`;
          document.getElementById(
            "step-indicator"
          ).textContent = `Step ${nextStep} of 4`;
        }, 500);
      }

      function validateStep(stepNumber) {
        // Clear previous validation messages
        clearValidationMessages(stepNumber);

        const validationSummary = document.getElementById(
          `validation-summary-step${stepNumber}`
        );
        const validationList = document.getElementById(
          `validation-list-step${stepNumber}`
        );
        const errors = [];

        // Validate passwords if on current step is 1
        if (stepNumber === 1) {
          validatePassword(document.getElementById('password').value);
          validatePasswordMatch(document.getElementById('confirm_password'), errors);
        }

        // Step 1 validation
        if (stepNumber === 1) {
          // Validate each required field in step 1
          validateField("first_name", "First Name is required", errors);
          validateField("last_name", "Last Name is required", errors);
          validateField("voter_id", "Voter ID is required", errors);
          validateField("email", "Email is required", errors);
          validateField("dob", "Date of Birth is required", errors);
          validateField("phone_number", "Phone Number is required", errors);
          validateField("password", "Password is required", errors);
          validateField(
            "confirm_password",
            "Confirm Password is required",
            errors
          );

          // Check if passwords match
          const password = document.getElementById("password");
          const confirmPassword = document.getElementById("confirm_password");
          if (password.value !== confirmPassword.value) {
            errors.push("Passwords do not match");
            confirmPassword.classList.add("error");
          }

          // Check if gender is selected
          const genderMale = document.getElementById("gender_male");
          const genderFemale = document.getElementById("gender_female");
          const genderOther = document.getElementById("gender_other");
          if (
            !genderMale.checked &&
            !genderFemale.checked &&
            !genderOther.checked
          ) {
            errors.push("Please select your gender");
          }
        }

        // Step 2 validation
        else if (stepNumber === 2) {
          validateField(
            "temporary_address",
            "Temporary Address is required",
            errors
          );
          validateField(
            "permanent_address",
            "Permanent Address is required",
            errors
          );
        }

        // Step 3 validation
        else if (stepNumber === 3) {
          const isProfileImageValid = validateFileField(
            "profile_image",
            "Profile Image is required",
            errors
          );
          const isHoldingCitizenshipValid = validateFileField(
            "image_holding_citizenship", 
            "Image Holding Citizenship is required",
            errors
          );
          const isThumbPrintValid = validateFileField(
            "thumb_print",
            "Thumb Print is required", 
            errors
          );

          if (!isProfileImageValid || !isHoldingCitizenshipValid || !isThumbPrintValid) {
            return false;
          }
        }

        // Step 4 validation
        else if (stepNumber === 4) {
          const isVoterCardValid = validateFileField(
                  "voter_card_front",
                  "Voter Card Front is required",
                  errors
          );
          const isCitizenshipFrontValid = validateFileField(
                  "citizenship_front",
                  "Citizenship Front is required",
                  errors
          );
          const isCitizenshipBackValid = validateFileField(
                  "citizenship_back",
                  "Citizenship Back is required",
                  errors
          );

          // Check terms checkbox
          const terms = document.getElementById("terms");
          if (!terms.checked) {
            errors.push("You must agree to the Terms and Conditions");
            const validationSummary = document.getElementById('validation-summary-step4');
            const validationList = document.getElementById('validation-list-step4');
            validationList.innerHTML = '<li>You must agree to the Terms and Conditions</li>';
            validationSummary.classList.add('visible');
          }

          // If all validations pass, allow form submission  
          if (errors.length === 0 && isVoterCardValid && isCitizenshipFrontValid && isCitizenshipBackValid && terms.checked) {
            document.getElementById("registrationForm").submit();
            return true;
          }
          return false;
        }

        // If there are errors, show them
        if (errors.length > 0) {
          // Clear previous errors
          validationList.innerHTML = "";

          // Add each error to the list
          errors.forEach((error) => {
            const li = document.createElement("li");
            li.textContent = error;
            validationList.appendChild(li);
          });

          // Show validation summary
          validationSummary.classList.add("visible");

          // Scroll to the validation summary
          validationSummary.scrollIntoView({
            behavior: "smooth",
            block: "start",
          });

          return false;
        }

        return true;
      }

      function validateField(fieldId, errorMessage, errors) {
        const field = document.getElementById(fieldId);
        const errorDiv = document.getElementById(`${fieldId}-error`) || createErrorDiv(fieldId);

        if (!field.value.trim()) {
          errors.push(errorMessage);
          field.classList.add("error");
          errorDiv.textContent = errorMessage;
          errorDiv.classList.add("visible");
        } else {
          field.classList.remove("error");
          errorDiv.classList.remove("visible");

          // Check password match when confirm password field is validated
          if (fieldId === 'confirm_password') {
            const password = document.getElementById('password');
            if (field.value !== password.value) {
              errors.push('Passwords do not match');
              field.classList.add("error");
              errorDiv.textContent = 'Passwords do not match';
              errorDiv.classList.add("visible");
            }
          }
        }
      }

      // Add event listeners to all form fields
      document.addEventListener('DOMContentLoaded', function () {
        const formFields = document.querySelectorAll('input:not([type="file"]), textarea');
        const fileFields = document.querySelectorAll('input[type="file"]');

        formFields.forEach(field => {
          if (field.id === 'password') {
            field.addEventListener('blur', function () {
              validatePassword(this.value);
            });
          } else if (field.id === 'confirm_password') {
            field.addEventListener('blur', function () {
              validatePasswordMatch(this, []);
            });
          }
        });

        fileFields.forEach(field => {
          field.addEventListener('change', function () {
            const fieldName = field.id.split('_').map(word =>
                    word.charAt(0).toUpperCase() + word.slice(1)
            ).join(' ');
            validateFileField(this.id, `${fieldName} is required`, []);
            validateFormField(this.id);
          });
        });

        // Add blur event listener for password field
        document.getElementById('password').addEventListener('blur', function () {
          validatePassword(this.value);
        });
      });

      function validateFormField(fieldId) {
        const errors = [];
        validateField(fieldId, `${fieldId.split('_').join(' ')} is required`, errors);

        // Additional validation for specific fields
        if (fieldId === 'email') {
          validateEmail(document.getElementById(fieldId), errors);
        } else if (fieldId === 'confirm_password') {
          validatePasswordMatch(document.getElementById(fieldId), errors);
        } else if (fieldId === 'dob') {
          validateDateOfBirth(document.getElementById(fieldId), errors);
        }
      }

      function validateDateOfBirth(field, errors) {
        const dob = new Date(field.value);
        const today = new Date();
        const age = today.getFullYear() - dob.getFullYear();
        const monthDiff = today.getMonth() - dob.getMonth();
        const actualAge = monthDiff < 0 || (monthDiff === 0 && today.getDate() < dob.getDate())
                ? age - 1
                : age;

        const errorDiv = document.getElementById('dob-error') || createErrorDiv('dob');
        if (actualAge < 18) {
          errors.push('You must be at least 18 years old to register');
          field.classList.add("error");
          errorDiv.textContent = 'You must be at least 18 years old to register';
          errorDiv.classList.add('visible');
        } else if (actualAge > 110) {
          errors.push('Please enter a valid date of birth');
          field.classList.add("error");
          errorDiv.textContent = 'Please enter a valid date of birth';
          errorDiv.classList.add('visible');
        } else if (actualAge > 110) {
          errors.push('Please enter a valid date of birth');
          field.classList.add("error");
          errorDiv.textContent = 'Please enter a valid date of birth';
          errorDiv.classList.add('visible');
        } else {
          field.classList.remove("error");
          errorDiv.classList.remove('visible');
        }
      }

      function validatePassword(password) {
        const errors = [];
        if (password.length < 8 || !/[A-Z]/.test(password) || !/[a-z]/.test(password) || !/[0-9]/.test(password) || !/[!@#$%^&*]/.test(password)) {
          errors.push("Password must be at least 8 characters and include an uppercase letter, lowercase letter, number, and special character (!@#$%^&*).");
          const passwordField = document.getElementById('password');
          if (passwordField) {
            passwordField.classList.add('error');
            const errorDiv = document.getElementById('password-error') || createErrorDiv('password');
            errorDiv.textContent = errors[0];
            errorDiv.classList.add('visible');
          }
        } else {
          const passwordField = document.getElementById('password');
          if (passwordField) {
            passwordField.classList.remove('error');
            const errorDiv = document.getElementById('password-error');
            if (errorDiv) {
              errorDiv.classList.remove('visible');
            }
          }
        }
        return errors;
      }
      function createErrorDiv(fieldId) {
        let errorDiv = document.getElementById(`${fieldId}-error`);
        if (!errorDiv) {
          errorDiv = document.createElement('div');
          errorDiv.className = 'error-message';
          errorDiv.id = `${fieldId}-error`;
          const field = document.getElementById(fieldId);
          field.parentNode.insertBefore(errorDiv, field.nextSibling);
        }
        return errorDiv;
      }

      function validateEmail(field, errors) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(field.value)) {
          errors.push('Please enter a valid email address');
          field.classList.add("error");
        }
      }

      function validatePasswordMatch(field, errors) {
        const password = document.getElementById('password');
        if (field.value !== password.value) {
          errors.push('Passwords do not match');
          field.classList.add("error");
        }
      }

      function validateFileField(fieldId, errorMessage, errors) {
        const field = document.getElementById(fieldId);
        const preview = document.getElementById(`${fieldId}_preview`);

        if (!field.files || field.files.length === 0) {
          field.classList.add("error");
          showFileError(fieldId, errorMessage);
          if (preview) preview.innerHTML = "";
          return false;
        }

        const file = field.files[0];
        const allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];
        const maxSize = 1.5 * 1024 * 1024; // 1.5MB

        if (!allowedTypes.includes(file.type)) {
          field.classList.add("error");
          showFileError(fieldId, 'Please upload only JPG, JPEG or PNG images');
          if (preview) preview.innerHTML = "";
          return false;
        }

        if (file.size > maxSize) {
          field.classList.add("error");
          const maxSizeStr = convertSizeToString(maxSize);
          const currentSize = convertSizeToString(file.size);
          showFileError(fieldId, `File size exceeds ${maxSizeStr} limit. Current Size: ${currentSize}. Please upload a file less than ${maxSizeStr}.`);
          if (preview) preview.innerHTML = "";
          return false;
        }

        field.classList.remove("error");
        clearFileError(fieldId);
        return true;
      }


      function showFileError(fieldId, message) {
        clearFileError(fieldId);
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        const fieldName = fieldId.split('_')
                .map(word => word.charAt(0).toUpperCase() + word.slice(1))
                .join(' ');
        errorDiv.textContent = message;
        errorDiv.id = `${fieldId}-error`;
        const field = document.getElementById(fieldId);
        field.parentNode.insertBefore(errorDiv, field.nextSibling);
        setTimeout(() => errorDiv.classList.add('visible'), 10);
      }

      function clearFileError(fieldId) {
        const existingError = document.getElementById(`${fieldId}-error`);
        if (existingError) {
          existingError.remove();
        }
      }

      function clearValidationMessages(stepNumber) {
        // Hide validation summary
        const validationSummary = document.getElementById(
          `validation-summary-step${stepNumber}`
        );
        validationSummary.classList.remove("visible");

        // Clear error classes from inputs in this step
        const stepElement = document.getElementById(`step${stepNumber}`);
        const inputs = stepElement.querySelectorAll("input, textarea");
        inputs.forEach((input) => {
          input.classList.remove("error");
        });
      }

      function prevStep(currentStep, prevStep) {
        // Apply slide-out animation to current step
        const currentStepEl = document.getElementById(`step${currentStep}`);
        currentStepEl.classList.add("slide-right");

        // Wait for animation to complete before showing previous step
        setTimeout(() => {
          currentStepEl.classList.remove("active", "slide-right");

          // Show previous step with slide-in animation
          const prevStepEl = document.getElementById(`step${prevStep}`);
          prevStepEl.classList.add("active", "slide-in-left");

          // Remove animation class after it completes
          setTimeout(() => {
            prevStepEl.classList.remove("slide-in-left");
          }, 500);

          // Update progress bar with animation
          const progressPercentage = (prevStep / 4) * 100;
          document.getElementById(
            "progress-bar"
          ).style.width = `${progressPercentage}%`;
          document.getElementById(
            "progress-percentage"
          ).textContent = `${progressPercentage}%`;
          document.getElementById(
            "step-indicator"
          ).textContent = `Step ${prevStep} of 4`;
        }, 500);
      }

      function previewImage(input, previewId) {
        const preview = document.getElementById(previewId);

        preview.innerHTML = '';

        if (!input.files || !input.files[0]) {
          return;
        }

        const file = input.files[0];
        const reader = new FileReader();

        reader.onload = function (e) {
          const img = document.createElement('img');
          img.src = e.target.result;
          img.style.maxWidth = '150px';
          img.style.height = 'auto';
          preview.appendChild(img);
        };
      
        reader.readAsDataURL(file);
      }

      function convertSizeToString(bytes) {
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
        if (bytes === 0) return '0 Byte';
        const i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
        return Math.round((bytes / Math.pow(1024, i)) * 100) / 100 + ' ' + sizes[i];
      }
    </script>
  </body>
</html>
</body>
</html>