CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    img_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    img_url VARCHAR(255)
);

CREATE TABLE muscle_groups (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    color VARCHAR(7) NOT NULL
);

CREATE TABLE muscles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    group_id INT,
    FOREIGN KEY (group_id) REFERENCES muscle_groups(id) ON DELETE CASCADE
);

CREATE TABLE exercise_muscles (
    exercise_id INT,
    muscle_id INT,
    PRIMARY KEY (exercise_id, muscle_id),
    FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE,
    FOREIGN KEY (muscle_id) REFERENCES muscles(id) ON DELETE CASCADE
);

CREATE TABLE workouts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    date DATE NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE set_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE sets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    workout_id INT,
    exercise_id INT,
    set_number INT NOT NULL,
    set_type_id INT,
    repetitions INT,
    weight DECIMAL(5,2),
    FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE,
    FOREIGN KEY (set_type_id) REFERENCES set_types(id) ON DELETE CASCADE
);

CREATE TABLE dropsets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    set_id INT,
    repetitions INT,
    weight DECIMAL(5,2),
    FOREIGN KEY (set_id) REFERENCES sets(id) ON DELETE CASCADE
);

CREATE INDEX idx_sets_workout_id ON sets(workout_id);
CREATE INDEX idx_dropset_details_set_id ON dropsets(set_id);