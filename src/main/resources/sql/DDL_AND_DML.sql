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
(1, 1, 'Можливість одного об\'єкта набувати властивостей іншого, що дозволяє будувати ієрархію класів.'),
(2, 1, 'Можливість доповнювати об\'єкт функціоналом. Можливість виступати об\'єкту у різних формах.'),
(3, 1, 'Надання об\'єкту параметрів, які чітко виділяє його на тлі інших, визначаючи його концептуальні межі.'),
(4, 1, 'Об\'єднання даних та коду, що маніпулює цими даними, захист їх від зовнішнього втручання або неправильного використання');

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
(2, 'Основи ООП', 14, 'Протягом курсу \'Основи програмування\' студенти освоять найпоширенішу і найпопулярнішу парадигму, яка використовується практично у всіх сучасних мовах - об\'єктно-орієнтоване програмування.', 'true');

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
(1, 'Принципи ООП', 2, 'true'),
(2, 'Історія ООП', 2, 'false');

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
(1, 'ООП в C++', 2, 'https://fis.tntu.edu.ua/data/elibrary/3/oop_cpp.pdf', 'true');

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
(1, 1, '1) Абстракція.\nХорошим прикладом абстракції у реальному житті є опис посад у компанії чи організації. Назва посади – це одна, а обов\'язки кожної конкретної посади – це вже зовсім інша.\n\nУяви, що ти проектуєш структуру своєї майбутньої компанії. Ти можеш розділити обов\'язки секретаря: «розкидати» їх за іншими посадами. Можеш розбити посаду виконавчого директора на кілька незалежних посад: фінансовий директор, технічний директор, директор з маркетингу, директор з персоналу. Або, наприклад, об\'єднати посади офіс-менеджера та рекрутера в одну.\n\nТи вигадуєш назви посад у своїй фірмі, а потім розкидаєш обов\'язки по цих посадах. Це і є абстракція – розбиття чогось великого, монолітного на безліч дрібних складових елементів.\n\nЗ погляду програмування, абстракція — це, скажімо, правильне поділ програми на об\'єкти.\n\nЗазвичай будь-яку велику програму можна десятками способів у вигляді взаємодіючих об\'єктів. Абстракція дозволяє відібрати основні параметри і опустити другорядні.\n\nАбстракція – це як стратегія у військовій справі. Погана стратегія – і жодною геніальною тактикою ситуацію не виправити.', 1),
(2, 2, '2) Інкапсуляція.\nМета інкапсуляції – покращити якість взаємодії речей за рахунок їх спрощення.\n\nА найкращий спосіб спростити щось – це приховати все складне від сторонніх очей. Наприклад, якщо тебе посадять у кабіну Боїнга, ти не одразу розберешся, як ним управляти:\n\nЗ іншого боку, для пасажирів літака все виглядає простіше: купив квиток, сів у літак, злетіли та приземлилися. Ти можеш легко перелетіти з континенту на континент, володіючи тільки навичками «купити квиток» і «сісти на літак». Всі складнощі у вигляді підготовки літака до польоту, зльоту, посадки та різноманітних позаштатних ситуацій приховані від нас. Не кажучи вже про супутникову навігацію, автопілот і диспетчерські центри в аеропортах. І це спрощує життя.\n\nЗ погляду програмування, інкапсуляція – це «приховування реалізації». Мені подобається таке визначення. Наш клас може містити сотні методів та реалізовувати дуже складну поведінку у різних ситуаціях. Але ми можемо приховати від сторонніх очей всі його методи (позначити модифікатором private), а для взаємодії з іншими класами залишити пару-трійку методів (позначити їх модифікатором public). Тоді всі інші класи нашої програми бачитимуть у цьому класі всього три методи, і викликатимуть саме їх. А всі складнощі будуть приховані всередині класу як кабіна пілотів від щасливих пасажирів.', 1),
(3, 3, '3) Успадкування.\nВ успадкування є дві сторони. Сторона програмування та сторона реального життя. З погляду програмування, успадкування – це спеціальне ставлення між двома класами. Але набагато цікавіше, що таке успадкування з погляду реального життя.\n\nЯкби нам знадобилося щось створити в реальному житті, то ми маємо два рішення:\n\n1) створити потрібну нам річ із нуля, витративши купу часу та сил.\n\n2) створити потрібну нам річ на основі вже існуючої.\n\nНайбільш оптимальна стратегія виглядає так: беремо існуюче гарне рішення, трохи доопрацьовуємо його, підганяємо під свої потреби і використовуємо.\n\nЯкщо ми простежимо історію виникнення людини, виявиться, що з моменту зародження життя на планеті пройшли мільярди років. А якщо уявити, що людина виникла з мавпи (на основі мавпи), то пройшла лише кілька мільйонів років. Створення з нуля – довше. Набагато довше.\n\nУ програмуванні також є можливість створювати один клас на основі іншого. Новий клас стає нащадком (спадкоємцем) вже існуючого. Це дуже вигідно, коли є клас, який містить 80-90% потрібних нам даних і методів. Ми просто оголошуємо відповідний клас батьком нашого нового класу, тоді в новому класі автоматично з\'являються всі дані та методи класу-батька.', 1),
(4, 4, '4) Поліморфізм.\nПоліморфізм – це поняття з галузі програмування. Воно визначає ситуацію, коли за одним інтерфейсом ховаються різні реалізації. Якщо постаратися пошукати його аналоги у реальному житті, то одним із таких аналогів буде процес керування машиною.\n\nЯкщо людина може керувати вантажівкою, то її можна посадити і за кермо швидкою, і за кермо спорткара. Людина може керувати машиною незалежно від того, що це за машина, тому що всі вони мають однаковий інтерфейс керування: кермо, педалі та важіль коробки передач. Внутрішній пристрій машин є різним, але всі вони мають однаковий інтерфейс управління.\n\nЯкщо повернутися до програмування, то поліморфізм дозволяє одноманітно звертатися до об\'єктів різних класів (яких зазвичай мають спільного предка) – річ, яку важко переоцінити. Цінність його тим вища, що більше програма.\n\nООП – це принципи. Внутрішні закони. Кожен із них нас у чомусь обмежує, даючи натомість великі переваги, коли програма зростає до більших розмірів. Чотири принципи ООП – це як чотири ніжки стільця. Забери хоч одну, і вся система стане нестійкою.', 1);

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
-- Дамп данных таблицы `question`
--

INSERT INTO `question` (`questionId`, `questionNum`, `text`, `answer`, `testId`) VALUES
(1, 1, 'Поліморфізм – це:', 'Можливість доповнювати об\'єкт функціоналом. Можливість виступати об\'єкту у різних формах.', 1),
(2, 2, 'Успадкування - це:', 'Можливість одного об\'єкта набувати властивостей іншого, що дозволяє будувати ієрархію класів.', 1);

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
(1, 'Тест 1', 900, 2, 'true');

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
(0, 'Іосиф Ліліанович Дрос', 'dros', '141c52354d0e16a720f7109451848c03d0b53ae92e427dfda80348e60d1e4716255bbbb3043bc391', 1); 
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