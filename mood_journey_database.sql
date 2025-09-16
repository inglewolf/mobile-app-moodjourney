-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 16, 2025 at 07:47 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mood_journey`
--

-- --------------------------------------------------------

--
-- Table structure for table `journalentry`
--

CREATE TABLE `journalentry` (
  `EntryID` int(5) NOT NULL,
  `UserID` int(3) NOT NULL,
  `Month` int(2) NOT NULL,
  `Year` int(4) NOT NULL,
  `Day` int(2) NOT NULL,
  `MoodID` tinyint(1) NOT NULL,
  `EntryTitle` varchar(25) DEFAULT NULL,
  `JournalEntry` mediumtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `journalentry`
--

INSERT INTO `journalentry` (`EntryID`, `UserID`, `Month`, `Year`, `Day`, `MoodID`, `EntryTitle`, `JournalEntry`) VALUES
(1, 2, 7, 2024, 1, 1, 'Positive Start This Month', 'This month has begun with a refreshing sense of optimism. The weather has been remarkably pleasant, contributing to a lighter mood and a more positive outlook. I\'ve set some new goals for myself, both personally and professionally, and I\'m excited about the potential to achieve them.\r\n\r\nIn terms of work, I’ve decided to focus on enhancing my skills and taking on new challenges. I’m looking into some online courses that can help me expand my knowledge and stay up-to-date with the latest trends in my field. It feels empowering to invest in my own growth and development.\r\n\r\nOn the personal front, I’ve made a commitment to prioritize my health and well-being. This includes regular exercise, a balanced diet, and setting aside time each day for mindfulness and relaxation. I’ve also started a new hobby - painting - which has been incredibly therapeutic and enjoyable.\r\n\r\nSpending quality time with family and friends has also been a highlight. We’ve planned a few small gatherings and outdoor activities that adhere to safety guidelines, allowing us to connect and have fun without any worries.\r\n\r\nOverall, I’m feeling grateful and motivated. The positive energy that has marked the start of this month is something I hope to carry forward in the coming weeks. Here’s to a month filled with growth, happiness, and fulfillment!'),
(2, 2, 7, 2024, 4, 2, 'Feeling Stressed Today', 'Today has been one of those days where everything seems to pile up at once. From the moment I woke up, I felt a sense of overwhelming pressure. Work deadlines are looming, and there are numerous tasks to juggle simultaneously. It feels like there aren’t enough hours in the day to get everything done.\r\n\r\nThe stress started with an early morning meeting that didn’t go as planned. Technical issues caused delays, and the discussions were more challenging than anticipated. This set the tone for the rest of the day, making it hard to regain my focus and productivity.\r\n\r\nIn addition to work stress, personal responsibilities have also been weighing heavily on my mind. There are errands to run, bills to pay, and household chores that need attention. It’s been difficult to find a moment to breathe and decompress.\r\n\r\nI’ve noticed that stress has impacted my mood and energy levels. I’ve been feeling more irritable and exhausted, and it’s been challenging to stay positive. Recognizing these feelings is the first step, though, and I’m trying to be gentle with myself.\r\n\r\nTo cope, I’ve decided to take a few proactive steps. First, I’ve made a list of priorities to tackle the most urgent tasks systematically. Breaking down tasks into smaller, manageable steps always helps reduce the feeling of being overwhelmed. Second, I’ve scheduled short breaks throughout the day to step away from work and clear my mind.\r\n\r\nI also reached out to a friend for a quick chat, which was immensely helpful. Sometimes, just talking to someone who understands can lighten the load. Additionally, I’ve planned a relaxing activity for the evening, such as a warm bath and a good book, to unwind and recharge.\r\n\r\nAlthough today has been tough, I know that stress is temporary and manageable. Taking care of my mental health is crucial, and I’m committed to finding balance amidst the chaos. Tomorrow is a new day, and I’m hopeful that it will bring a fresh perspective and renewed energy.'),
(3, 2, 7, 2024, 7, 1, 'Great Weekend with Family', 'This past weekend was truly wonderful, filled with quality time spent with my family. It was a much-needed break from the usual routine, and I feel so refreshed and happy.\r\n\r\nWe started the weekend with a fun game night on Friday. Everyone gathered around, and we played our favorite board games. Laughter filled the room as we shared jokes and friendly competition. It was a perfect way to kick off the weekend, and it set a joyful tone for the days ahead.\r\n\r\nOn Saturday, we decided to have a picnic at the nearby park. The weather was perfect – sunny with a gentle breeze. We packed a delicious lunch, including sandwiches, fresh fruit, and homemade cookies. The kids enjoyed running around and playing games, while the adults relaxed and caught up on life. It was so peaceful and enjoyable to be outdoors, surrounded by nature.\r\n\r\nSunday was all about family bonding at home. We cooked a big breakfast together, with everyone pitching in to make pancakes, eggs, and bacon. After breakfast, we spent the afternoon watching movies and sharing stories from our childhood. It was heartwarming to hear everyone’s memories and experiences, and it brought us even closer together.\r\n\r\nIn the evening, we gathered for a family dinner. We made a feast with everyone’s favorite dishes, and the meal was filled with conversation and laughter. After dinner, we took a walk around the neighborhood, enjoying the cool evening air and the beautiful sunset. It was a perfect way to end the weekend.\r\n\r\nSpending time with my family always brings a sense of comfort and joy. This weekend was a reminder of how important it is to cherish these moments and create lasting memories. I feel so grateful for my family and the love and support we share. This weekend was truly a great one, and I’m looking forward to many more like it.'),
(4, 2, 7, 2024, 10, 3, 'Tough Day at Work', 'Today was one of those challenging days at work that really tested my patience and resilience. From the moment I logged in, it felt like everything was going against me. The workload was heavier than usual, and unexpected issues kept cropping up.\r\n\r\nThe day started with a critical project meeting. The discussion quickly turned into a heated debate, with differing opinions on the best approach to move forward. It was difficult to reach a consensus, and the tension in the room was palpable. Despite the disagreements, I tried to remain calm and focused, contributing my ideas and listening to others.\r\n\r\nAfter the meeting, I had a series of tasks to complete, each with tight deadlines. As I worked through my to-do list, technical problems began to surface. My computer crashed twice, causing me to lose some unsaved work. This setback added to my frustration and made it hard to stay on track.\r\n\r\nMidway through the day, I received feedback on a recent project. While constructive, the feedback highlighted several areas for improvement. It was disheartening to realize that my hard work hadn\'t met the expectations. I took a moment to process the feedback and remind myself that it\'s an opportunity to learn and grow, even though it felt like a setback.\r\n\r\nTo top it off, an urgent request came in just before the end of the day. It required immediate attention and added to the already mounting pressure. I worked late to address the issue, ensuring it was resolved before signing off for the day.\r\n\r\nDespite the challenges, I tried to focus on the positives. I managed to keep my composure during the meeting, completed most of my tasks, and addressed the urgent request promptly. I also reached out to a colleague for support, and we collaborated to solve one of the technical problems, which was a silver lining in an otherwise tough day.\r\n\r\nToday was a reminder that not every day at work will be smooth sailing. Challenges are inevitable, but they also provide valuable learning experiences. I’m proud of how I handled the stress and stayed productive despite the obstacles. Tomorrow is a new day, and I’m determined to approach it with a fresh perspective and renewed energy.'),
(5, 2, 7, 2024, 15, 2, 'Personal Issues Today', 'Today has been particularly challenging on the personal front. I found myself struggling with a few issues that have been weighing heavily on my mind. It started early in the morning when I received some troubling news from a close friend, which set a negative tone for the rest of the day. I spent a lot of time reflecting on the situation and trying to find a solution, but it felt like nothing was going as planned.\r\n\r\nAt work, it was difficult to focus because my mind kept drifting back to the personal matters. I had a few important tasks to complete, but my productivity took a hit. I tried to stay positive and push through, but it was a struggle. I found myself reaching out to a couple of colleagues for support, and their encouragement helped a bit, though it didn’t completely alleviate the stress.\r\n\r\nIn the evening, I took some time to unwind and decompress. I went for a long walk, which was a good way to clear my head and gain some perspective. I also spoke with a family member, which provided some much-needed comfort and advice.\r\n\r\nOverall, today has been a reminder of how personal issues can affect different aspects of life, including work and overall well-being. I’m hoping that with time and support, I can navigate through these challenges and come out stronger. Tomorrow is a new day, and I’m determined to approach it with a fresh mindset.'),
(6, 2, 7, 2024, 20, 1, 'Optimistic About Future', 'Today, I find myself feeling particularly hopeful about the future. There’s a sense of excitement in the air as I reflect on the progress I’ve made and the opportunities that lie ahead. Despite some recent challenges, I’m starting to see a clearer path forward, and it’s invigorating.\r\n\r\nI’ve been working on setting new goals and planning for the coming months. Whether it’s personal development, career aspirations, or even small projects, I feel a renewed sense of purpose and enthusiasm. The positive changes I’ve made recently are beginning to bear fruit, and I can’t help but feel that things are moving in the right direction.\r\n\r\nThe support I’ve received from friends and family has been invaluable, and their encouragement has played a significant role in boosting my confidence. I’m also starting to see the fruits of my labor in my work and personal life, which only adds to my optimism.\r\n\r\nLooking ahead, I’m excited about the potential for growth and the new experiences that await. There are still hurdles to overcome, but I’m confident that with persistence and a positive mindset, I can tackle whatever comes my way. The future feels promising, and I’m ready to embrace it with open arms.\r\n\r\n'),
(7, 2, 7, 2024, 23, 3, 'Tough Day but Got Through', 'Today has been one of those days that tested my patience and resilience. From the moment I woke up, it felt like everything was working against me. Tasks that seemed straightforward turned into major challenges, and I struggled to stay focused and productive.\r\n\r\nThe workload was heavier than usual, and I found myself facing unexpected problems that required quick thinking and problem-solving. There were moments when I felt overwhelmed and questioned whether I would make it through the day. However, despite the difficulties, I managed to push through and get everything done.\r\n\r\nI took breaks when I could to recharge, and even though it wasn’t easy, I tried to maintain a positive attitude. Reaching out to colleagues for help and sharing a few laughs with them made a difference, reminding me that I’m not alone in this.\r\n\r\nBy the end of the day, I felt a sense of accomplishment and relief. It’s days like these that remind me of my strength and ability to handle adversity. I’m grateful that I managed to get through it, and I’m looking forward to a better day tomorrow.'),
(8, 2, 7, 2024, 30, 1, 'Month-End Reflections', 'As the month draws to a close, I find myself reflecting on the past few weeks and the journey I’ve been on. It’s been a month of both challenges and achievements, and taking a moment to look back has provided me with valuable insights.\r\n\r\nThis month started with a lot of anticipation and excitement for new projects and goals. While there were a few setbacks along the way, I’m proud of how I managed to navigate through them. It’s clear that persistence and a positive mindset have been key in overcoming obstacles.\r\n\r\nI’ve had the chance to make significant progress on several personal and professional fronts. Achievements, both big and small, have contributed to a sense of accomplishment. It’s been fulfilling to see some of the hard work pay off and to receive positive feedback from colleagues and loved ones.\r\n\r\nOn the flip side, there have been moments of stress and uncertainty. Reflecting on these experiences, I realize the importance of balance and self-care. The challenges have taught me a lot about resilience and the value of taking things one step at a time.\r\n\r\nOverall, this month has been a period of growth and learning. As I look ahead to the next month, I’m carrying forward the lessons learned and the momentum built. Here’s to a new chapter filled with more opportunities, continued progress, and a hopeful outlook.'),
(14, 2, 8, 2024, 2, 1, 'Excited for August', 'As August begins, I find myself brimming with excitement and anticipation for what the month has in store. This summer has been full of promise, and August feels like the perfect time to embrace new opportunities and experiences.\r\n\r\nI\'ve got several projects lined up that I\'ve been eagerly preparing for, and I’m thrilled to see them come to fruition. There\'s a fresh energy in the air, and I’m ready to dive into my goals with renewed vigor. Whether it\'s personal development, work-related initiatives, or simply enjoying the summer days, I feel a strong sense of optimism.\r\n\r\nThe weather has been fantastic, with sunny days and warm temperatures making everything seem a bit brighter. I’ve planned a few outings and activities that I’m really looking forward to, including a weekend trip to the countryside and some outdoor gatherings with friends. It’s these little moments that add joy and excitement to life.\r\n\r\nAugust also marks the beginning of a few new routines and habits that I’ve been wanting to implement. I’m optimistic that these changes will bring positive results and help me stay focused and motivated. I’m looking forward to tracking my progress and celebrating the small victories along the way.\r\n\r\nOverall, there’s a sense of adventure and possibility that accompanies the start of this month. I’m ready to make the most of it, embrace the challenges, and savor the successes. Here’s to a fantastic August filled with growth, joy, and memorable moments!\n\nI \'ve added some \'quotes\'.'),
(15, 2, 8, 2024, 5, 3, 'Overwhelmed with Work', 'Today has been particularly challenging, and I’m feeling quite overwhelmed by the sheer volume of work on my plate. From the moment I started my day, it seemed like tasks kept piling up faster than I could handle them. Every project and deadline feels urgent, and it’s been difficult to keep up with everything.\r\n\r\nThe constant flow of emails, meetings, and unexpected issues has made it hard to focus on any one task for too long. I’ve been trying to prioritize and manage my time effectively, but it feels like no matter how much I do, there’s always more to be done. The pressure is starting to get to me, and I’m finding it hard to keep a positive mindset amidst the chaos.\r\n\r\nI had to ask for help from a few colleagues, which was a bit uncomfortable but necessary. Their support has been invaluable, and it’s a reminder that I’m not alone in this. I need to remember that it’s okay to lean on others and not take everything on by myself.\r\n\r\nIn the midst of this, I’m trying to find moments to take a step back and breathe. I’ve scheduled short breaks to clear my mind, but it’s a challenge to fully unwind when the workload is so intense. I’m hoping that by staying organized and tackling tasks one by one, I can start to regain control.\r\n\r\nTonight, I plan to make a list of everything I need to address and prioritize the most urgent items. Hopefully, this will help me manage the workload more effectively and reduce some of the stress. Tomorrow is a new day, and I’m determined to face it with a clear plan and a bit more resilience.');

-- --------------------------------------------------------

--
-- Table structure for table `mood`
--

CREATE TABLE `mood` (
  `MoodID` tinyint(1) NOT NULL,
  `Img` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mood`
--

INSERT INTO `mood` (`MoodID`, `Img`) VALUES
(1, 'happy.png'),
(2, 'neutral.png'),
(3, 'sad.png');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` int(3) NOT NULL,
  `Username` varchar(25) NOT NULL,
  `Email` varchar(200) NOT NULL,
  `Birthdate` varchar(10) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `Username`, `Email`, `Birthdate`, `Password`) VALUES
(2, 'inglewolf', 'ingle@email.com', '9-7-1996', 'sharky'),
(5, 'ananda', 'ananda@email.com', '9-7-1996', 'hammy'),
(7, 'ingleduck', 'inglewolf.mix@gmail.com', '11-8-2001', 'password');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `journalentry`
--
ALTER TABLE `journalentry`
  ADD PRIMARY KEY (`EntryID`),
  ADD UNIQUE KEY `unique_date` (`Month`,`Day`,`Year`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `MoodID` (`MoodID`);

--
-- Indexes for table `mood`
--
ALTER TABLE `mood`
  ADD PRIMARY KEY (`MoodID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `journalentry`
--
ALTER TABLE `journalentry`
  MODIFY `EntryID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `journalentry`
--
ALTER TABLE `journalentry`
  ADD CONSTRAINT `journalentry_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`),
  ADD CONSTRAINT `journalentry_ibfk_2` FOREIGN KEY (`MoodID`) REFERENCES `mood` (`MoodID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
