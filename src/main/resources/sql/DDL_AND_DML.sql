SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `simple_learning`
--

-- --------------------------------------------------------

DROP TABLE `answer`;
DROP TABLE `question`;
DROP TABLE `result`;
DROP TABLE `test`;
DROP TABLE `page`;
DROP TABLE `lecture`;
DROP TABLE `material`;
DROP TABLE `coursemembership`;
DROP TABLE `course`;
DROP TABLE `role`;
DROP TABLE `user`;

-- --------------------------------------------------------

--
-- Table `answer`
--

CREATE TABLE `answer` (
  `answerId` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `answer` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `answer` (`answerId`, `questionId`, `answer`) VALUES
(1, 1, '��������� ������ ��\'���� �������� ������������ ������, �� �������� �������� �������� �����.'),
(2, 1, '��������� ����������� ��\'��� ������������. ��������� ��������� ��\'���� � ����� ������.'),
(3, 1, '������� ��\'���� ���������, �� ����� ������ ���� �� �� �����, ���������� ���� ������������ ���.'),
(4, 1, '��\'������� ����� �� ����, �� �������� ���� ������, ������ �� �� ���������� ��������� ��� ������������� ������������');

-- --------------------------------------------------------

--
-- Table `course`
--

CREATE TABLE `course` (
  `courseId` int(11) NOT NULL,
  `title` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `teacherId` int(11) NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `isPublic` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `course` (`courseId`, `title`, `teacherId`, `description`, `isPublic`) VALUES
(2, '������ ���', 14, '�������� ����� \'������ �������������\' �������� ������� ������������ � ������������� ���������, ��� ��������������� ��������� � ��� �������� ����� - ��\'�����-��������� �������������.', 'true');

-- --------------------------------------------------------

--
-- Table `coursemembership`
--

CREATE TABLE `coursemembership` (
  `courseId` int(11) DEFAULT NULL,
  `studentId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table `lecture`
--

CREATE TABLE `lecture` (
  `lectureId` int(11) NOT NULL,
  `title` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `courseId` int(11) NOT NULL,
  `isPublic` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `lecture` (`lectureId`, `title`, `courseId`, `isPublic`) VALUES
(1, '�������� ���', 2, 'true'),
(2, '������ ���', 2, 'false');

-- --------------------------------------------------------

--
-- Table `material`
--

CREATE TABLE `material` (
  `materialId` int(11) NOT NULL,
  `title` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `courseId` int(11) NOT NULL,
  `link` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `isPublic` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `material` (`materialId`, `title`, `courseId`, `link`, `isPublic`) VALUES
(1, '��� � C++', 2, 'https://fis.tntu.edu.ua/data/elibrary/3/oop_cpp.pdf', 'true');

-- --------------------------------------------------------

--
-- Table `page`
--

CREATE TABLE `page` (
  `pageId` int(11) NOT NULL,
  `pageNum` int(11) NOT NULL,
  `text` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lectureId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `page` (`pageId`, `pageNum`, `text`, `lectureId`) VALUES
(1, 1, '1) ����������.\n������� ��������� ���������� � ��������� ���� � ���� ����� � ������ �� ����������. ����� ������ � �� ����, � ����\'���� ����� ��������� ������ � �� ��� ����� ����.\n\n����, �� �� �������� ��������� �� ��������� ������. �� ����� �������� ����\'���� ���������: ���������� �� �� ������ ��������. ����� ������� ������ ����������� ��������� �� ����� ���������� �����: ���������� ��������, �������� ��������, �������� � ����������, �������� � ���������. ���, ���������, ��\'������ ������ ����-��������� �� ��������� � ����.\n\n�� ������� ����� ����� � ���� ����, � ���� �������� ����\'���� �� ��� �������. �� � � ���������� � �������� ������ ��������, ���������� �� ����� ������ ��������� ��������.\n\n� ������� �������������, ���������� � ��, ������, ��������� ���� �������� �� ��\'����.\n\n�������� ����-��� ������ �������� ����� ��������� ������� � ������ ���������� ��\'����. ���������� �������� ������� ������ ��������� � �������� ���������.\n\n���������� � �� �� �������� � �������� �����. ������ �������� � � ������ ��������� �������� �������� �� ���������.', 1),
(2, 2, '2) ������������.\n���� ������������ � ��������� ����� �����䳿 ����� �� ������� �� ���������.\n\n� ��������� ����� ��������� ���� � �� ��������� ��� ������� �� �������� ����. ���������, ���� ���� �������� � ����� �����, �� �� ������ ����������, �� ��� ���������:\n\n� ������ ����, ��� �������� ����� ��� ������� �������: ����� ������, �� � ����, ������ �� ������������. �� ����� ����� ��������� � ���������� �� ���������, �������� ����� ��������� ������� ������ � ����� �� ����. �� ��������� � ������ ��������� ����� �� �������, ������, ������� �� ����������� ����������� �������� �������� �� ���. �� ������ ��� ��� ����������� ��������, �������� � ������������ ������ � ����������. � �� ������ �����.\n\n� ������� �������������, ������������ � �� ������������� ����������. ��� ���������� ���� ����������. ��� ���� ���� ������ ���� ������ �� ������������ ���� ������� �������� � ����� ���������. ��� �� ������ ��������� �� �������� ���� �� ���� ������ (��������� ������������� private), � ��� �����䳿 � ������ ������� �������� ����-����� ������ (��������� �� ������������� public). ��� �� ���� ����� ���� �������� ���������� � ����� ���� ������ ��� ������, � ������������� ���� ��. � �� ��������� ������ �������� �������� ����� �� ����� ����� �� �������� ��������.', 1),
(3, 3, '3) ������������.\n� ������������ � �� �������. ������� ������������� �� ������� ��������� �����. � ������� �������������, ������������ � �� ���������� ��������� �� ����� �������. ��� �������� �������, �� ���� ������������ � ������� ��������� �����.\n\n���� ��� ����������� ���� �������� � ��������� ����, �� �� ���� ��� ������:\n\n1) �������� ������� ��� �� �� ����, ���������� ���� ���� �� ���.\n\n2) �������� ������� ��� �� �� ����� ��� �������.\n\n������� ���������� �������� ������� ���: ������ ������� ����� ������, ����� ������������� ����, ��������� �� ��� ������� � �������������.\n\n���� �� ���������� ������ ���������� ������, ���������, �� � ������� ���������� ����� �� ������ ������� ������� ����. � ���� ������, �� ������ ������� � ����� (�� ����� �����), �� ������� ���� ����� ������� ����. ��������� � ���� � �����. �������� �����.\n\n� ������������ ����� � ��������� ���������� ���� ���� �� ����� ������. ����� ���� ��� �������� (����������) ��� ���������. �� ���� ������, ���� � ����, ���� ������ 80-90% �������� ��� ����� � ������. �� ������ ��������� ��������� ���� ������� ������ ������ �����, ��� � ������ ���� ����������� �\'��������� �� ��� �� ������ �����-������.', 1),
(4, 4, '4) ����������.\n���������� � �� ������� � ����� �������������. ���� ������� ��������, ���� �� ����� ����������� ��������� ��� ���������. ���� ����������� �������� ���� ������� � ��������� ����, �� ����� �� ����� ������� ���� ������ ��������� �������.\n\n���� ������ ���� �������� ����������, �� �� ����� �������� � �� ����� �������, � �� ����� ���������. ������ ���� �������� ������� ��������� �� ����, �� �� �� ������, ���� �� �� ���� ����� ��������� ��������� ���������: �����, ����� �� ����� ������� �������. �������� ������� ����� � �����, ��� �� ���� ����� ��������� ��������� ���������.\n\n���� ����������� �� �������������, �� ���������� �������� ���������� ���������� �� ��\'���� ����� ����� (���� �������� ����� �������� ������) � ��, ��� ����� �����������. ֳ����� ���� ��� ����, �� ����� ��������.\n\n��� � �� ��������. ������� ������. ����� �� ��� ��� � ������ ������, ����� �������� ����� ��������, ���� �������� ������ �� ������ ������. ������ �������� ��� � �� �� ������ ���� ������. ������ ��� ����, � ��� ������� ����� ��������.', 1);

-- --------------------------------------------------------

--
-- Table`question`
--

CREATE TABLE `question` (
  `questionId` int(11) NOT NULL,
  `questionNum` int(11) NOT NULL,
  `text` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `testId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- ���� ������ ������� `question`
--

INSERT INTO `question` (`questionId`, `questionNum`, `text`, `answer`, `testId`) VALUES
(1, 1, '���������� � ��:', '��������� ����������� ��\'��� ������������. ��������� ��������� ��\'���� � ����� ������.', 1),
(2, 2, '������������ - ��:', '��������� ������ ��\'���� �������� ������������ ������, �� �������� �������� �������� �����.', 1);

-- --------------------------------------------------------

--
-- Table `result`
--

CREATE TABLE `result` (
  `resultId` int(11) NOT NULL,
  `score` float NOT NULL,
  `testId` int(11) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table `role`
--

CREATE TABLE `role` (
  `roleId` int(11) NOT NULL,
  `roleName` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `role` (`roleId`, `roleName`) VALUES
(1, 'ADMIN'),
(2, 'TEACHER'),
(3, 'STUDENT');

-- --------------------------------------------------------

--
-- Table `test`
--

CREATE TABLE `test` (
  `testId` int(11) NOT NULL,
  `title` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` int(10) NOT NULL,
  `courseId` int(11) NOT NULL,
  `isPublic` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `test` (`testId`, `title`, `time`, `courseId`, `isPublic`) VALUES
(1, '���� 1', 900, 2, 'true');

-- --------------------------------------------------------

--
-- Table `user`
--

CREATE TABLE `user` (
  `userId` int(11) NOT NULL,
  `name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `login` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `roleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table `user`
--

INSERT INTO `user` (`userId`, `name`, `login`, `password`, `roleId`) VALUES
(0, '����� ˳������� ����', 'dros', '141c52354d0e16a720f7109451848c03d0b53ae92e427dfda80348e60d1e4716255bbbb3043bc391', 1); 
-- Password: dros

-- --------------------------------------------------------

ALTER TABLE `answer`
  ADD PRIMARY KEY (`answerId`),
  ADD KEY `questionId` (`questionId`);

ALTER TABLE `course`
  ADD PRIMARY KEY (`courseId`),
  ADD KEY `teacherId` (`teacherId`);

ALTER TABLE `coursemembership`
  ADD KEY `courseId` (`courseId`),
  ADD KEY `studentId` (`studentId`);

ALTER TABLE `lecture`
  ADD UNIQUE KEY `lectureId` (`lectureId`),
  ADD KEY `courseId` (`courseId`);

ALTER TABLE `material`
  ADD PRIMARY KEY (`materialId`),
  ADD KEY `courseId` (`courseId`);

ALTER TABLE `page`
  ADD PRIMARY KEY (`pageId`),
  ADD KEY `lectureId` (`lectureId`);

ALTER TABLE `question`
  ADD PRIMARY KEY (`questionId`),
  ADD KEY `testId` (`testId`);

ALTER TABLE `result`
  ADD PRIMARY KEY (`resultId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `testId` (`testId`);

ALTER TABLE `role`
  ADD PRIMARY KEY (`roleId`);

ALTER TABLE `test`
  ADD PRIMARY KEY (`testId`),
  ADD KEY `courseId` (`courseId`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`),
  ADD KEY `roleId` (`roleId`);

--
-- AUTO_INCREMENT
--

ALTER TABLE `answer`
  MODIFY `answerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

ALTER TABLE `course`
  MODIFY `courseId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

ALTER TABLE `lecture`
  MODIFY `lectureId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

ALTER TABLE `material`
  MODIFY `materialId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

ALTER TABLE `page`
  MODIFY `pageId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

ALTER TABLE `question`
  MODIFY `questionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

ALTER TABLE `result`
  MODIFY `resultId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

ALTER TABLE `role`
  MODIFY `roleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `test`
  MODIFY `testId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

ALTER TABLE `user`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

-- --------------------------------------------------------

ALTER TABLE `answer`
  ADD CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`questionId`) REFERENCES `question` (`questionId`);

ALTER TABLE `course`
  ADD CONSTRAINT `course_ibfk_1` FOREIGN KEY (`teacherId`) REFERENCES `user` (`userId`);

ALTER TABLE `coursemembership`
  ADD CONSTRAINT `coursemembership_ibfk_1` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`),
  ADD CONSTRAINT `coursemembership_ibfk_2` FOREIGN KEY (`studentId`) REFERENCES `user` (`userId`);

ALTER TABLE `lecture`
  ADD CONSTRAINT `lecture_ibfk_1` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`);

ALTER TABLE `material`
  ADD CONSTRAINT `material_ibfk_1` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`),
  ADD CONSTRAINT `material_ibfk_2` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`);

ALTER TABLE `page`
  ADD CONSTRAINT `page_ibfk_1` FOREIGN KEY (`lectureId`) REFERENCES `lecture` (`lectureId`);

ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`testId`) REFERENCES `test` (`testId`);

ALTER TABLE `result`
  ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  ADD CONSTRAINT `result_ibfk_2` FOREIGN KEY (`testId`) REFERENCES `test` (`testId`);

ALTER TABLE `test`
  ADD CONSTRAINT `test_ibfk_1` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`);

ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`roleId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;