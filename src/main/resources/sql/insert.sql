INSERT INTO manufacturers VALUES
	(1, 'EXEED'), (2, 'Hyundai'), (3, 'Kia'), (4, 'Skoda');

INSERT INTO models VALUES
	(1, 'LX', 1), (2, 'TXL', 1), (3, 'TXL 2.0', 1),
	(4, 'TUCSON', 2), (5, 'SANTA FE', 2), (6, 'SONATA', 2),
	(7, 'Seltos', 3), (8, 'Ceed', 3), (9, 'Sportage', 3),
	(10, 'RAPID', 4), (11, 'KAROQ', 4), (12, 'SUPERB', 4);

INSERT INTO cars VALUES
	(1, 1, '1GTHK891X458VW35F', 2022, 2155437, ARRAY ['мультимедиа-система', 'обогрев сидений', 'GPS-навигатор', 'кондиционер', 'радио'], '{"upholstery": "Хром","color": "Красный"}', '{"engine_v": 2.9,"engine_p": 150,"fuel_cons": 19,"num_doors": 6,"num_seats": 2,"trunk_v": 205,"transmission_type": "Вариатор","cruise_control": "Есть","fuel_type": "Дизель"}', '{"last_checkup": 2021,"mileage": 44979}', TRUE), 
	(2, 1, '1FMNU42PX4WDU2GBX', 2006, 1356145, ARRAY ['кондиционер', 'обогрев сидений'], '{"upholstery": "Винил","color": "Белый"}', '{"engine_v": 2.6,"engine_p": 86,"fuel_cons": 22,"num_doors": 6,"num_seats": 4,"trunk_v": 588,"transmission_type": "Механика","cruise_control": "Нет","fuel_type": "Дизель"}', '{"last_checkup": 2023,"mileage": 26348}', TRUE), 
	(3, 1, '1D3HW58NX6W2V1TZC', 2000, 1478974, ARRAY ['мультимедиа-система', 'обогрев руля', 'кондиционер', 'GPS-навигатор'], '{"upholstery": "Карпет","color": "Серебристый"}', '{"engine_v": 2.3,"engine_p": 148,"fuel_cons": 18,"num_doors": 4,"num_seats": 2,"trunk_v": 341,"transmission_type": "Вариатор","cruise_control": "Нет","fuel_type": "Дизель"}', '{"last_checkup": 2022,"mileage": 63551}', TRUE), 
	(4, 2, '1GCCS695X1KE33HD4', 2011, 3106464, ARRAY ['обогрев руля', 'кондиционер', 'обогрев сидений'], '{"upholstery": "Искусственная коджа","color": "Красный"}', '{"engine_v": 2.9,"engine_p": 145,"fuel_cons": 6,"num_doors": 6,"num_seats": 4,"trunk_v": 455,"transmission_type": "Вариатор","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2021,"mileage": 67701}', TRUE), 
	(5, 2, '1MEHM40WX9CVTUZAL', 2014, 2515892, ARRAY ['кондиционер', 'мультимедиа-система', 'GPS-навигатор', 'обогрев руля', 'обогрев сидений'], '{"upholstery": "Кожа","color": "Чёрный"}', '{"engine_v": 2.8,"engine_p": 127,"fuel_cons": 23,"num_doors": 2,"num_seats": 2,"trunk_v": 764,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2023,"mileage": 117806}', TRUE), 
	(6, 2, '1J4FT27SXVVKTPXC4', 2003, 2348415, ARRAY ['обогрев сидений', 'обогрев руля'], '{"upholstery": "Кожа","color": "Чёрный"}', '{"engine_v": 2.2,"engine_p": 141,"fuel_cons": 19,"num_doors": 4,"num_seats": 2,"trunk_v": 361,"transmission_type": "Робот","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2020,"mileage": 133701}', TRUE), 
	(7, 3, '3FTWX325X8GC2UDLT', 2017, 1550262, ARRAY ['GPS-навигатор', 'обогрев руля', 'кондиционер'], '{"upholstery": "Кожа","color": "Белый"}', '{"engine_v": 1.5,"engine_p": 84,"fuel_cons": 6,"num_doors": 4,"num_seats": 4,"trunk_v": 440,"transmission_type": "Автомат","cruise_control": "Есть","fuel_type": "Дизель"}', '{"last_checkup": 2019,"mileage": 49044}', TRUE), 
	(8, 3, '2GCHC23GX55S1FEND', 2016, 2556013, ARRAY ['GPS-навигатор'], '{"upholstery": "Алькантра","color": "Синий"}', '{"engine_v": 3.2,"engine_p": 90,"fuel_cons": 23,"num_doors": 6,"num_seats": 6,"trunk_v": 688,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-95"}', '{"last_checkup": 2019,"mileage": 112743}', TRUE), 
	(9, 3, '1GTJC34UX1Y7UZP1J', 2003, 1991406, ARRAY ['GPS-навигатор', 'обогрев руля', 'мультимедиа-система', 'кондиционер', 'радио'], '{"upholstery": "Искусственная коджа","color": "Серебристый"}', '{"engine_v": 1.2,"engine_p": 104,"fuel_cons": 16,"num_doors": 4,"num_seats": 2,"trunk_v": 788,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-95"}', '{"last_checkup": 2021,"mileage": 136031}', TRUE), 
	(10, 4, '1FDSE34SX8BD2E7AB', 2017, 3380497, ARRAY ['кондиционер'], '{"upholstery": "Кожа","color": "Серебристый"}', '{"engine_v": 2.8,"engine_p": 110,"fuel_cons": 20,"num_doors": 4,"num_seats": 6,"trunk_v": 380,"transmission_type": "Робот","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2023,"mileage": 40644}', TRUE), 
	(11, 4, '3B7MC33WX11WL89K8', 2010, 1774131, ARRAY ['GPS-навигатор', 'обогрев сидений'], '{"upholstery": "Алькантра","color": "Синий"}', '{"engine_v": 3.0,"engine_p": 119,"fuel_cons": 10,"num_doors": 2,"num_seats": 6,"trunk_v": 528,"transmission_type": "Робот","cruise_control": "Есть","fuel_type": "АИ-95"}', '{"last_checkup": 2021,"mileage": 129404}', TRUE), 
	(12, 4, '1GCGK34JXT3P1WBYY', 2022, 2589338, ARRAY ['обогрев сидений', 'обогрев руля', 'GPS-навигатор', 'кондиционер'], '{"upholstery": "Хром","color": "Зелёный"}', '{"engine_v": 3.0,"engine_p": 102,"fuel_cons": 18,"num_doors": 4,"num_seats": 2,"trunk_v": 206,"transmission_type": "Автомат","cruise_control": "Нет","fuel_type": "АИ-92"}', '{"last_checkup": 2022,"mileage": 41720}', TRUE), 
	(13, 5, '3GKGK26FXVUN8FE7A', 2006, 2522145, ARRAY ['обогрев руля', 'мультимедиа-система'], '{"upholstery": "Кожа","color": "Синий"}', '{"engine_v": 3.3,"engine_p": 125,"fuel_cons": 13,"num_doors": 2,"num_seats": 6,"trunk_v": 706,"transmission_type": "Автомат","cruise_control": "Нет","fuel_type": "АИ-95"}', '{"last_checkup": 2022,"mileage": 25919}', TRUE), 
	(14, 5, '1G6DB5EYXBL6Z295X', 2003, 1024278, ARRAY ['обогрев сидений'], '{"upholstery": "Алькантра","color": "Жёлтый"}', '{"engine_v": 1.1,"engine_p": 121,"fuel_cons": 12,"num_doors": 6,"num_seats": 2,"trunk_v": 307,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-95"}', '{"last_checkup": 2020,"mileage": 91820}', TRUE), 
	(15, 5, '1B3AJ46UX1NDTVSPW', 2005, 1408468, ARRAY ['обогрев руля', 'радио', 'обогрев сидений', 'мультимедиа-система', 'GPS-навигатор'], '{"upholstery": "Винил","color": "Красный"}', '{"engine_v": 1.8,"engine_p": 146,"fuel_cons": 7,"num_doors": 4,"num_seats": 2,"trunk_v": 609,"transmission_type": "Робот","cruise_control": "Нет","fuel_type": "АИ-95"}', '{"last_checkup": 2023,"mileage": 50643}', TRUE), 
	(16, 6, '3FTSF305X8SC1WE7V', 2015, 1979104, ARRAY ['радио'], '{"upholstery": "Винил","color": "Серебристый"}', '{"engine_v": 3.1,"engine_p": 93,"fuel_cons": 6,"num_doors": 2,"num_seats": 2,"trunk_v": 491,"transmission_type": "Робот","cruise_control": "Нет","fuel_type": "Дизель"}', '{"last_checkup": 2023,"mileage": 94810}', TRUE), 
	(17, 6, '1GTHK89DX68C85LNC', 2005, 1600883, ARRAY ['обогрев руля', 'радио'], '{"upholstery": "Алькантра","color": "Чёрный"}', '{"engine_v": 2.0,"engine_p": 129,"fuel_cons": 14,"num_doors": 2,"num_seats": 2,"trunk_v": 211,"transmission_type": "Механика","cruise_control": "Нет","fuel_type": "Дизель"}', '{"last_checkup": 2023,"mileage": 109025}', TRUE), 
	(18, 6, '1B4GP24RXV6LA3K8B', 2010, 2199177, ARRAY ['обогрев сидений'], '{"upholstery": "Кожа","color": "Зелёный"}', '{"engine_v": 3.1,"engine_p": 100,"fuel_cons": 6,"num_doors": 6,"num_seats": 4,"trunk_v": 402,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "Дизель"}', '{"last_checkup": 2019,"mileage": 147370}', TRUE), 
	(19, 7, '4F4DR17XXW6L78257', 2006, 3132489, ARRAY ['радио'], '{"upholstery": "Кожа","color": "Белый"}', '{"engine_v": 3.3,"engine_p": 87,"fuel_cons": 6,"num_doors": 4,"num_seats": 4,"trunk_v": 207,"transmission_type": "Автомат","cruise_control": "Нет","fuel_type": "АИ-98"}', '{"last_checkup": 2022,"mileage": 69688}', TRUE), 
	(20, 7, 'JH4KA966X152UKSE4', 2023, 1196191, ARRAY ['обогрев руля', 'кондиционер', 'обогрев сидений', 'GPS-навигатор', 'мультимедиа-система'], '{"upholstery": "Искусственная коджа","color": "Жёлтый"}', '{"engine_v": 2.2,"engine_p": 91,"fuel_cons": 15,"num_doors": 4,"num_seats": 6,"trunk_v": 460,"transmission_type": "Робот","cruise_control": "Нет","fuel_type": "Дизель"}', '{"last_checkup": 2021,"mileage": 101448}', TRUE), 
	(21, 7, '1GCZGFFAXASU9RMAG', 2001, 1960658, ARRAY ['кондиционер'], '{"upholstery": "Искусственная коджа","color": "Белый"}', '{"engine_v": 3.3,"engine_p": 81,"fuel_cons": 24,"num_doors": 2,"num_seats": 6,"trunk_v": 616,"transmission_type": "Робот","cruise_control": "Нет","fuel_type": "АИ-95"}', '{"last_checkup": 2020,"mileage": 65692}', TRUE), 
	(22, 8, 'WVWNE63BX47DT3HTB', 2020, 1182199, ARRAY ['мультимедиа-система'], '{"upholstery": "Карпет","color": "Белый"}', '{"engine_v": 3.5,"engine_p": 101,"fuel_cons": 15,"num_doors": 4,"num_seats": 6,"trunk_v": 362,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2022,"mileage": 28434}', TRUE), 
	(23, 8, '1N4AB41DXXDB38R8W', 2013, 3328508, ARRAY ['кондиционер', 'радио', 'мультимедиа-система', 'обогрев руля', 'обогрев сидений'], '{"upholstery": "Винил","color": "Зелёный"}', '{"engine_v": 2.9,"engine_p": 106,"fuel_cons": 19,"num_doors": 6,"num_seats": 4,"trunk_v": 213,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-95"}', '{"last_checkup": 2022,"mileage": 68981}', TRUE), 
	(24, 8, '2GTHK231X4S3HGBYN', 2022, 2107313, ARRAY ['радио', 'мультимедиа-система', 'обогрев руля', 'обогрев сидений', 'кондиционер'], '{"upholstery": "Хром","color": "Жёлтый"}', '{"engine_v": 2.5,"engine_p": 114,"fuel_cons": 5,"num_doors": 2,"num_seats": 6,"trunk_v": 410,"transmission_type": "Автомат","cruise_control": "Есть","fuel_type": "Дизель"}', '{"last_checkup": 2022,"mileage": 29791}', TRUE), 
	(25, 9, '2S3TD03VXTZ8VXPN4', 2016, 1504733, ARRAY ['обогрев сидений', 'обогрев руля', 'радио', 'GPS-навигатор', 'кондиционер'], '{"upholstery": "Кожа","color": "Жёлтый"}', '{"engine_v": 1.9,"engine_p": 108,"fuel_cons": 14,"num_doors": 2,"num_seats": 2,"trunk_v": 407,"transmission_type": "Автомат","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2023,"mileage": 58306}', TRUE), 
	(26, 9, '2GBD4E31X34DC8LW5', 2022, 1684420, ARRAY ['кондиционер', 'мультимедиа-система', 'радио', 'GPS-навигатор'], '{"upholstery": "Кожа","color": "Серебристый"}', '{"engine_v": 2.1,"engine_p": 125,"fuel_cons": 16,"num_doors": 4,"num_seats": 2,"trunk_v": 530,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-92"}', '{"last_checkup": 2021,"mileage": 78078}', TRUE), 
	(27, 9, '1D7HU18DX5BBAYMZY', 2008, 2571159, ARRAY ['обогрев сидений', 'обогрев руля', 'мультимедиа-система', 'GPS-навигатор', 'радио'], '{"upholstery": "Хром","color": "Красный"}', '{"engine_v": 1.7,"engine_p": 109,"fuel_cons": 19,"num_doors": 2,"num_seats": 4,"trunk_v": 479,"transmission_type": "Вариатор","cruise_control": "Нет","fuel_type": "Дизель"}', '{"last_checkup": 2021,"mileage": 100359}', TRUE), 
	(28, 10, '4M2CU52EXWFJFXRXD', 2021, 2381821, ARRAY ['кондиционер', 'обогрев сидений', 'мультимедиа-система', 'обогрев руля', 'GPS-навигатор'], '{"upholstery": "Хром","color": "Синий"}', '{"engine_v": 2.2,"engine_p": 81,"fuel_cons": 13,"num_doors": 2,"num_seats": 6,"trunk_v": 683,"transmission_type": "Вариатор","cruise_control": "Есть","fuel_type": "АИ-95"}', '{"last_checkup": 2020,"mileage": 20958}', TRUE), 
	(29, 10, '3GCJC89UX3WSA98F2', 2023, 2261439, ARRAY ['кондиционер'], '{"upholstery": "Алькантра","color": "Зелёный"}', '{"engine_v": 2.3,"engine_p": 128,"fuel_cons": 6,"num_doors": 6,"num_seats": 4,"trunk_v": 275,"transmission_type": "Автомат","cruise_control": "Нет","fuel_type": "АИ-92"}', '{"last_checkup": 2023,"mileage": 126867}', TRUE), 
	(30, 10, '1FTRF07LX3X798TFJ', 2009, 1563181, ARRAY ['GPS-навигатор', 'радио'], '{"upholstery": "Кожа","color": "Жёлтый"}', '{"engine_v": 3.2,"engine_p": 119,"fuel_cons": 19,"num_doors": 2,"num_seats": 4,"trunk_v": 776,"transmission_type": "Механика","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2020,"mileage": 61361}', TRUE), 
	(31, 11, 'WDW9E745X73860727', 2009, 2337139, ARRAY ['обогрев сидений', 'обогрев руля', 'радио', 'GPS-навигатор', 'мультимедиа-система'], '{"upholstery": "Винил","color": "Чёрный"}', '{"engine_v": 1.5,"engine_p": 102,"fuel_cons": 25,"num_doors": 4,"num_seats": 4,"trunk_v": 607,"transmission_type": "Робот","cruise_control": "Нет","fuel_type": "АИ-92"}', '{"last_checkup": 2020,"mileage": 145313}', TRUE), 
	(32, 11, '1GTHK842X4XVV3W15', 2010, 1867110, ARRAY ['кондиционер', 'обогрев руля', 'обогрев сидений'], '{"upholstery": "Алькантра","color": "Белый"}', '{"engine_v": 1.2,"engine_p": 115,"fuel_cons": 17,"num_doors": 2,"num_seats": 2,"trunk_v": 514,"transmission_type": "Робот","cruise_control": "Есть","fuel_type": "Дизель"}', '{"last_checkup": 2019,"mileage": 137597}', TRUE), 
	(33, 11, '1GCJTCBPXBUWJEEX6', 2020, 1863352, ARRAY ['обогрев сидений', 'кондиционер'], '{"upholstery": "Винил","color": "Серебристый"}', '{"engine_v": 2.8,"engine_p": 141,"fuel_cons": 15,"num_doors": 2,"num_seats": 6,"trunk_v": 694,"transmission_type": "Автомат","cruise_control": "Нет","fuel_type": "АИ-92"}', '{"last_checkup": 2021,"mileage": 89712}', TRUE), 
	(34, 12, '2B7HB11YXTKLKUP9T', 2014, 2395115, ARRAY ['радио', 'кондиционер', 'обогрев руля', 'GPS-навигатор'], '{"upholstery": "Алькантра","color": "Жёлтый"}', '{"engine_v": 2.3,"engine_p": 102,"fuel_cons": 25,"num_doors": 2,"num_seats": 4,"trunk_v": 718,"transmission_type": "Автомат","cruise_control": "Есть","fuel_type": "Дизель"}', '{"last_checkup": 2022,"mileage": 100034}', TRUE), 
	(35, 12, '3GNDA65XX92EHAX0F', 2003, 1312030, ARRAY ['кондиционер', 'обогрев сидений', 'GPS-навигатор'], '{"upholstery": "Карпет","color": "Чёрный"}', '{"engine_v": 3.5,"engine_p": 139,"fuel_cons": 20,"num_doors": 6,"num_seats": 2,"trunk_v": 373,"transmission_type": "Механика","cruise_control": "Нет","fuel_type": "АИ-95"}', '{"last_checkup": 2020,"mileage": 117187}', TRUE), 
	(36, 12, 'WDBHA23EXVPJ0TKHJ', 2002, 3481857, ARRAY ['радио', 'GPS-навигатор', 'мультимедиа-система'], '{"upholstery": "Алькантра","color": "Зелёный"}', '{"engine_v": 2.5,"engine_p": 136,"fuel_cons": 15,"num_doors": 4,"num_seats": 2,"trunk_v": 669,"transmission_type": "Вариатор","cruise_control": "Есть","fuel_type": "АИ-98"}', '{"last_checkup": 2022,"mileage": 24782}', TRUE);

INSERT INTO clients VALUES
	(1, 'Ситников Евлампий Измаилович', 'г. Североуральск, пер. Социалистический, д. 1 к. 31, 893598', 'bikovapolina@gmail.com', '79677523329'), 
	(2, 'Вероника Валериевна Цветкова', 'п. Агинское (Забайк.), алл. Коллективная, д. 7/5, 533315', 'shcherbakovvjacheslav@yahoo.com', '79973952694'), 
	(3, 'Иван Вилорович Андреев', 'п. Сочи, наб. Харьковская, д. 84 стр. 58, 917383', 'vitali_2010@rambler.ru', '79888911580'), 
	(4, 'Евдокия Сергеевна Родионова', 'ст. Кирово-Чепецк, ул. Кузнечная, д. 14, 203575', 'ljubov_1986@mail.ru', '79577810309'), 
	(5, 'Данилова Дарья Альбертовна', 'клх Сыктывкар, ш. Поперечное, д. 73 стр. 632, 241041', 'filatovanonna@gmail.com', '79408492254');

INSERT INTO orders VALUES
	(1, 3, 4, TIMESTAMP '2020-03-31 00:03:00', TRUE, 'Завершён'), 
	(2, 5, 35, TIMESTAMP '2019-11-10 00:11:00', TRUE, 'Ожидание оплаты'), 
	(3, 5, 6, TIMESTAMP '2022-03-04 00:03:00', TRUE, 'В работе'), 
	(4, 5, 11, TIMESTAMP '2020-11-24 00:11:00', FALSE, 'Отменён'), 
	(5, 5, 36, TIMESTAMP '2023-02-08 00:02:00', FALSE, 'Отменён'), 
	(6, 2, 12, TIMESTAMP '2020-01-13 00:01:00', TRUE, 'В работе'), 
	(7, 3, 31, TIMESTAMP '2020-02-02 00:02:00', TRUE, 'Оплачен'), 
	(8, 2, 30, TIMESTAMP '2021-04-30 00:04:00', TRUE, 'На тест-драйве');