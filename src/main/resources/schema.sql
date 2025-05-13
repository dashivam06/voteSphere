
--   Unverified users before admin approval
CREATE TABLE IF NOT EXISTS unverified_users (
    unverified_user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    voter_id VARCHAR(32) UNIQUE NOT NULL,
    notification_email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(255) UNIQUE,
    profile_image VARCHAR(255) UNIQUE NOT NULL,
    image_holding_citizenship VARCHAR(255) UNIQUE NOT NULL,
    voter_card_front VARCHAR(255) UNIQUE NOT NULL,
    citizenship_front VARCHAR(255) UNIQUE NOT NULL,
    citizenship_back VARCHAR(255) UNIQUE NOT NULL,
    thumb_print VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(255) NOT NULL,
    temporary_address VARCHAR(255) NOT NULL,
    permanent_address VARCHAR(255) NOT NULL,
    role ENUM('voter','admin') DEFAULT 'voter',
    is_verified BOOLEAN DEFAULT FALSE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--   Verified users in the system
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    voter_id VARCHAR(32) UNIQUE NOT NULL,
    notification_email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(255) UNIQUE,
    profile_image VARCHAR(255) UNIQUE NOT NULL,
    image_holding_citizenship VARCHAR(255) UNIQUE NOT NULL,
    voter_card_front VARCHAR(255) UNIQUE NOT NULL,
    citizenship_front VARCHAR(255) UNIQUE NOT NULL,
    citizenship_back VARCHAR(255) UNIQUE NOT NULL,
    thumb_print VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(255) NOT NULL,
    temporary_address VARCHAR(255) NOT NULL,
    permanent_address VARCHAR(255) NOT NULL,
    role ENUM('voter','admin') DEFAULT 'voter',
    is_verified BOOLEAN DEFAULT FALSE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--   Email verification and password reset tokens
CREATE TABLE IF NOT EXISTS tokens (
    token_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    type ENUM('email_verification','password_reset') NOT NULL,
    ip VARCHAR(255) NOT NULL,
    device VARCHAR(255) NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

--   Elections being conducted
CREATE TABLE IF NOT EXISTS elections (
    election_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL,
    cover_image VARCHAR(255) UNIQUE NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

--   Political parties
CREATE TABLE IF NOT EXISTS parties (
    party_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    leader_name VARCHAR(100),
    founder_name VARCHAR(100),
    symbol_image VARCHAR(255),
    cover_image VARCHAR(255),
    description TEXT
);

--   Candidates in elections
CREATE TABLE IF NOT EXISTS candidates (
    candidate_id INT AUTO_INCREMENT PRIMARY KEY,
    is_independent BOOLEAN DEFAULT FALSE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    dob DATE NOT NULL,
    gender VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    highest_education VARCHAR(255),
    bio TEXT,
    party_id INT,
    election_id INT NOT NULL,
    manifesto TEXT,
    FOREIGN KEY (party_id) REFERENCES parties(party_id),
    FOREIGN KEY (election_id) REFERENCES elections(election_id)
);

--  Voting records
CREATE TABLE IF NOT EXISTS votes (
    vote_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    election_id INT NOT NULL,
    party_id INT NOT NULL,
    voted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip VARCHAR(45) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (election_id) REFERENCES elections(election_id),
    FOREIGN KEY (party_id) REFERENCES parties(party_id)
);

--   Donations made by users
CREATE TABLE IF NOT EXISTS donations (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount INT NOT NULL,
    product_code VARCHAR(255) NOT NULL,
    transaction_uuid VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    donation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);