-- ==========================================
-- COP_3060 - Cameron Brown
-- Initial Database Seed Data
-- ==========================================

-- ==========================================
-- Clear existing data (optional - for development)
-- ==========================================
-- DELETE FROM user_favorite_movies;
-- DELETE FROM hobbies;
-- DELETE FROM contacts;
-- DELETE FROM movies;
-- DELETE FROM users;

-- ==========================================
-- INSERT USERS
-- ==========================================
INSERT INTO users (first_name, last_name, email, password, is_subscribed, browser, likes_site, created_at, updated_at)
VALUES 
    ('Cameron', 'Brown', 'cameron.brown@famu.edu', 'password123', true, 'chrome', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('John', 'Doe', 'john.doe@example.com', 'pass456', true, 'firefox', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Jane', 'Smith', 'jane.smith@example.com', 'pass789', false, 'safari', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Alice', 'Johnson', 'alice.johnson@example.com', 'alicepass', true, 'edge', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Bob', 'Williams', 'bob.williams@example.com', 'bobpass', false, 'chrome', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ==========================================
-- INSERT MOVIES (Cameron's Favorites)
-- ==========================================
INSERT INTO movies (title, genre, year_released, description, rating, created_at)
VALUES 
    ('Inception', 'Sci-Fi', 2010, 'A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 8.8, CURRENT_TIMESTAMP),
    ('The Dark Knight', 'Action', 2008, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 9.0, CURRENT_TIMESTAMP),
    ('Spirited Away', 'Animation', 2001, 'During her family''s move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits.', 8.6, CURRENT_TIMESTAMP),
    ('The Matrix', 'Sci-Fi', 1999, 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.', 8.7, CURRENT_TIMESTAMP),
    ('Interstellar', 'Sci-Fi', 2014, 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', 8.6, CURRENT_TIMESTAMP),
    ('Pulp Fiction', 'Crime', 1994, 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.', 8.9, CURRENT_TIMESTAMP),
    ('The Shawshank Redemption', 'Drama', 1994, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 9.3, CURRENT_TIMESTAMP),
    ('Avengers: Endgame', 'Action', 2019, 'After the devastating events of Infinity War, the Avengers assemble once more to reverse Thanos'' actions and restore balance to the universe.', 8.4, CURRENT_TIMESTAMP),
    ('Your Name', 'Animation', 2016, 'Two strangers find themselves linked in a bizarre way. When a connection forms, will distance be the only thing to keep them apart?', 8.4, CURRENT_TIMESTAMP),
    ('The Lord of the Rings: The Return of the King', 'Fantasy', 2003, 'Gandalf and Aragorn lead the World of Men against Sauron''s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 9.0, CURRENT_TIMESTAMP);

-- ==========================================
-- INSERT HOBBIES (Cameron's Hobbies)
-- ==========================================
INSERT INTO hobbies (name, description, category, user_id, created_at)
VALUES 
    -- Cameron's Hobbies (user_id = 1)
    ('Gaming', 'Playing video games, especially RPGs and action-adventure games', 'TECHNOLOGY', 1, CURRENT_TIMESTAMP),
    ('Camping', 'Outdoor camping and hiking in nature', 'OUTDOOR', 1, CURRENT_TIMESTAMP),
    ('Coding', 'Programming and software development projects', 'TECHNOLOGY', 1, CURRENT_TIMESTAMP),
    ('Reading', 'Reading sci-fi and fantasy novels', 'INDOOR', 1, CURRENT_TIMESTAMP),
    ('Hiking', 'Exploring trails and mountains', 'OUTDOOR', 1, CURRENT_TIMESTAMP),
    ('Traveling', 'Visiting new places and experiencing different cultures', 'OUTDOOR', 1, CURRENT_TIMESTAMP),
    
    -- John's Hobbies (user_id = 2)
    ('Photography', 'Capturing moments through the lens', 'ARTS', 2, CURRENT_TIMESTAMP),
    ('Cooking', 'Experimenting with new recipes', 'INDOOR', 2, CURRENT_TIMESTAMP),
    
    -- Jane's Hobbies (user_id = 3)
    ('Painting', 'Creating art with watercolors and acrylics', 'ARTS', 3, CURRENT_TIMESTAMP),
    ('Yoga', 'Practicing mindfulness and flexibility', 'SPORTS', 3, CURRENT_TIMESTAMP),
    
    -- Alice's Hobbies (user_id = 4)
    ('Running', 'Morning jogs and marathon training', 'SPORTS', 4, CURRENT_TIMESTAMP),
    ('Gardening', 'Growing vegetables and flowers', 'OUTDOOR', 4, CURRENT_TIMESTAMP),
    
    -- Bob's Hobbies (user_id = 5)
    ('Music', 'Playing guitar and piano', 'ARTS', 5, CURRENT_TIMESTAMP),
    ('Chess', 'Strategic board game competitions', 'INDOOR', 5, CURRENT_TIMESTAMP);

-- ==========================================
-- INSERT CONTACTS (Form Submissions)
-- ==========================================
INSERT INTO contacts (first_name, last_name, email, message, created_at)
VALUES 
    ('Michael', 'Scott', 'michael.scott@dundermifflin.com', 'Love the website! Would like to connect about collaboration opportunities.', CURRENT_TIMESTAMP),
    ('Sarah', 'Connor', 'sarah.connor@resistance.com', 'Great portfolio! Interested in discussing potential projects.', CURRENT_TIMESTAMP),
    ('Tony', 'Stark', 'tony@starkindustries.com', 'Impressive work on the JavaScript implementations. Let''s talk tech!', CURRENT_TIMESTAMP),
    ('Bruce', 'Wayne', 'bruce@wayneenterprises.com', 'Looking for talented developers. Your skills are remarkable.', CURRENT_TIMESTAMP),
    ('Peter', 'Parker', 'peter.parker@dailybugle.com', 'Fellow FAMU student here! Would love to study together sometime.', CURRENT_TIMESTAMP);

-- ==========================================
-- INSERT USER_FAVORITE_MOVIES (Many-to-Many)
-- ==========================================
-- Cameron's favorites (user_id = 1)
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (1, 1); -- Inception
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (1, 2); -- The Dark Knight
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (1, 3); -- Spirited Away
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (1, 4); -- The Matrix
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (1, 5); -- Interstellar

-- John's favorites (user_id = 2)
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (2, 6); -- Pulp Fiction
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (2, 7); -- The Shawshank Redemption
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (2, 2); -- The Dark Knight

-- Jane's favorites (user_id = 3)
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (3, 3); -- Spirited Away
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (3, 9); -- Your Name
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (3, 5); -- Interstellar

-- Alice's favorites (user_id = 4)
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (4, 8); -- Avengers: Endgame
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (4, 10); -- LOTR: Return of the King
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (4, 1); -- Inception

-- Bob's favorites (user_id = 5)
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (5, 4); -- The Matrix
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (5, 7); -- The Shawshank Redemption
INSERT INTO user_favorite_movies (user_id, movie_id) VALUES (5, 6); -- Pulp Fiction

-- ==========================================
-- Verification Queries (for testing)
-- ==========================================
-- SELECT * FROM users;
-- SELECT * FROM movies;
-- SELECT * FROM hobbies;
-- SELECT * FROM contacts;
-- SELECT * FROM user_favorite_movies;

-- Count records
-- SELECT 'users' as table_name, COUNT(*) as count FROM users
-- UNION ALL
-- SELECT 'movies', COUNT(*) FROM movies
-- UNION ALL
-- SELECT 'hobbies', COUNT(*) FROM hobbies
-- UNION ALL
-- SELECT 'contacts', COUNT(*) FROM contacts
-- UNION ALL
-- SELECT 'user_favorite_movies', COUNT(*) FROM user_favorite_movies;
