# --- !Ups

#CATEGORY

INSERT INTO "category"("name") VALUES("procesory");
INSERT INTO "category"("name") VALUES("karty graficzne");
INSERT INTO "category"("name") VALUES("pamięć RAM");
INSERT INTO "category"("name") VALUES("dyski twarde");
INSERT INTO "category"("name") VALUES("obudowy");

#BRAND

INSERT INTO "brand"("name") VALUES("intel");
INSERT INTO "brand"("name") VALUES("AMD");
INSERT INTO "brand"("name") VALUES("GOODRAM");
INSERT INTO "brand"("name") VALUES("ADATA");
INSERT INTO "brand"("name") VALUES("CRUCIAL");
INSERT INTO "brand"("name") VALUES("ASUS");
INSERT INTO "brand"("name") VALUES("GIGABYTE");
INSERT INTO "brand"("name") VALUES("MSI");
INSERT INTO "brand"("name") VALUES("PATRIOT");
INSERT INTO "brand"("name") VALUES("CORSAIR");
INSERT INTO "brand"("name") VALUES("SILENTIUM PC");
INSERT INTO "brand"("name") VALUES("BE QUIET!");
INSERT INTO "brand"("name") VALUES("ZALMAN");

#PRODUCT

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("Intel Core i5-9400F 2.90GHz 9MB","Intel Core i5-9400F to zaawansowany procesor 9-tej generacji pozbawiony zintegrowanego układu graficznego. CPU posiada 6 rdzeni, 6 wątków, 9MB pamięci SmartCache oraz częstotliwość bazową 2.90GHz, którą można zwiększyć do 4.10GHz w trybie Turbo. Poczuj moc 9. generacji procesorów Intel Core.",1,799)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("Intel Core i9-9900K","Poznaj najlepszy gamingowy procesor na świecie. Intel Core i9-9900K posiada aż 8 rdzeni fizycznych oraz 16 wątków, które taktowane bazowym zegarem 3,6 GHz zapewniają niezwykle wysoką wydajność. Świetnie poradzi sobie także z obróbką zdjęć czy filmów w rozdzielczości 4K. Nie zostawaj w tyle – wybierz Intel Core i9-9900K. Sprawdź, jak produkt wygląda w rzeczywistości. Chwyć zdjęcie poniżej i przeciągnij je w lewo lub prawo aby obrócić produkt lub skorzystaj z przycisków nawigacyjnych.",1,1,2499)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("AMD Ryzen 5 3600","Procesor AMD Ryzen 5 3600 korzysta z nowatorskiej architektury Zen 2. To dzięki niej CPU osiąga znakomite wyniki w grach i podczas obsługi profesjonalnych aplikacji, utrzymując stale wysoką wydajność. Jednostka posiada 6 rdzeni i 12 wątków, a pamięć cache liczy 35 MB. Rdzenie taktowane są zegarami bazowymi 3,60 GHz, które wzrasta w trybie Turbo do 4,20 GHz i dostarcza jeszcze więcej mocy do pracy oraz rozrywki.",1,2,849)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("AMD Ryzen 7 3800X","Procesor AMD Ryzen 7 3800X wprowadzi Cię na nowym poziom wydajności. Ten potężny CPU posiada 8 rdzeni o bazowej częstotliwości taktowania zegarów 3,90 GHz. W trybie Turbo przyspiesza ono do 4,50 GHz, zapewniając ogromną moc do płynnego gamingu oraz bezbłędny multitasking zaawansowanych operacji. Jednostka czerpie z całego spektrum możliwości architektury Zen 2, jej chłodzeniem zajmuje się wentylator Wraith Prism z oświetleniem RGB LED.",1,2,1699)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("GOODRAM 480GB 2,5 SATA SSD CL100","Ten przystępny dysk SSD CL100 gen.2 od firmy GOODRAM bazuje na wyselekcjonowanych pamięciach TLC NAND flash oraz kontrolerze Marvell 1120. W połączeniu z tradycyjnym interfejsem SATA III (6 Gb/s), CL100 gen.2 pozwoli przyśpieszyć komputer ze zwykłym dyskiem HDD nawet 10-krotnie, zapewniając jednocześnie stabilność pracy i niezawodność przez długi czas. Będzie stanowić również świetną podstawę do budowy konfiguracji zupełnie nowego komputera.",4,3,269)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("ADATA 512GB M.2 PCIe NVMe XPG SX8200 Pro","Ten przeznaczony dla entuzjastów PC oraz zapalonych graczy i overclockerów dysk SSD jest wyposażony w bardzo szybki interfejs M.2 PCIe Gen3 x4, zapewniający utrzymywane maks. szybkości odczytu/zapisu na poziomie nawet 3500/2300 MB/s, pozostawiając daleko w tyle możliwości tradycyjnego napędu HDD.",4,4,419)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("Crucial 240GB 2,5 SATA SSD BX500","Przyspiesz działanie swojego komputera, wyposażając go w nośnik flash Crucial BX500 - najprostszy sposób, aby cieszyć się wydajnością nowoczesnych komputerów niewielkim kosztem. Ten wyposażony w interfejs SATA III, 2,5-calowy dysk SSD stanowi doskonałą bazę pod system operacyjny i programy użytkowe, gwarantując uzyskanie wyjątkowo krótkiego czasu rozruchu komputera i błyskawicznych operacjach na danych.",4,5,229)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("MSI Radeon RX 570 ARMOR OC 4GB GDDR5","Architektura GCN czwartej generacji została zaprojektowana z myślą o graczach, którzy grają we wszystkie możliwe rodzaje gier, począwszy od najnowszych gier MOBA (Multiplayer Online Battle Arena) na najpopularniejszych, wysokobudżetowych nastawionych na najwyższą jakość produkcjach klasy AAA skończywszy. Asynchroniczne shadery (jednostki cieniujące) i udoskonalony silnik geometrii (Geometry Engine) sprawiają, że karta graficzna przenosi użytkownika na nowy poziom wydajności i płynnej rozgrywki.",2,8,719)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("Gigabyte GeForce GTX 1660 SUPER OC 6GB GDDR6","Przygotuj się na szybki i niezawodny gaming o wyjątkowych efektach graficznych, z nowymi układami w architekturze NVIDIA Turing. Gigabyte GeForce GTX 1660 SUPER OC to fabrycznie podkręcania konstrukcja wyposażona w 6GB pamięci GDDR6 oraz 192-bitowy interfejs pamięci. Wydajne chłodzenie Windforce 2X zadba o optymalne temperatury, jednocześnie wyłączając się podczas mniej obciążających GPU zadań. Całą konstrukcję dodatkowo usztywnia od spodu stylowy backplate.",2,7,1189)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("ASUS Radeon RX 570 STRIX OC 8GB GDDR5","Karta graficzna dla graczy ROG Strix RX 570 jest napakowana ekskluzywnymi technologiami firmy ASUS i oferuje m.in. technologię DirectCU II z opatentowanymi wentylatorami Wing-Blade, które zapewniają nawet 30% więcej chłodzenia przy 3x cichszej pracy, a także wiodącą w branży technologię Auto-Extreme zapewniającą wysoką jakość oraz niezawodność produktu. Oświetlenie Aura RGB umożliwia personalizację systemu gamingowego. ROG Strix RX 570 wyposażona jest również w GPU Tweak II z Xsplit Gamecaster zapewniający bardziej intuicyjną optymalizację działania i natychmiastowy streaming gry.",2,6,799)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("Patriot 16GB (2x8GB) 3200MHz CL16 Viper 4","Seria Viper to pamięci doskonale sprawdzające się przy podkręcaniu komputerów wyposażonych w procesory Intel Core. Pamięci Viper są wykonane z najwyższej jakości komponentów i poddane zostały dokładnym testom jakości oraz kompatybilności. Oferują taktowanie 3200MHz oraz opóźnienie na poziomie CL16.",3,9,399)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("Corsair 16GB (2x8GB) 3000MHz CL16 Vengeance LPX Black","Tam gdzie liczy się najwyższa wydajność, tam do gry wkracza pamięć RAM Corsair Vengeance LPX. Unikatowe radiatory, wykonane w całości z aluminium, znakomicie rozpraszają ciepło. To zapewnia bezkompromisową wydajność i maksymalne osiągi. Kiedy sytuacja no polu bitwy się zaognia Twoja pamięć RAM zachowuje zimną krew. Agresywny design podkreśli gamingowy styl Twojego zestawu, szczególnie gdy korzystasz z obudowy z oknem na panelu bocznym.",3,10,399)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("GOODRAM 8GB(1x8GB) 2400MHz CL15 IRIDIUM Black","GOODRAM IRDM DDR4 to moduły stworzone z myślą o graczach, entuzjastach i profesjonalistach. Pamięci zbudowane zostały z wyselekcjonowanych kości oraz 8-warstwowej płytki PCB. Całość zamknięto w efektowne, odprowadzające nadmiar ciepła radiatory, dostępne w kilku wariantach kolorystycznych.",3,3,169)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("SilentiumPC Signum SG1 TG","Zapewnij swojemu desktopowi niepowtarzalną oprawę z czarną obudową SilentiumPC Signum SG1 TG. Ta oryginalna konstrukcja wyróżnia się minimalistycznym gamingowym designem, uzupełnionym przez przeszklony panel boczny i siatkę na przednim panelu. Możesz więc z dumą wyeksponować stworzoną konfigurację. Przestronne wnętrze pomieści wszystkie niezbędne komponenty, a fabryczny system chłodzenia zapewni odpowiednią wentylację.",5,11,269)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("be quiet! Pure Base 500 Window Black","Obudowa do komputera be quiet! Pure Base 500 Window Black łączy w sobie ciszę i możliwość konfiguracji z kompaktowym i wyrafinowanym designem. Podstawowa górna pokrywa doskonale uzupełnia projekt Pure Base 500. Dołączona pod nią mata izolacyjna zapewnia cichą pracę. Jest to idealny wybór dla systemów chłodzonych powietrzem i zwykłych, cichych konfiguracji komputera. Mocne magnesy zapewniają szczelność górnej pokrywy. Właściwym wyborem dla wszystkich, którzy dążą do maksymalnej wydajności, a szczególnie do systemów chłodzonych wodą będzie górna pokrywa maksymalnie przepuszczająca powietrze. Wykonana jest z lekkiej siatki zapewniającej możliwie najlepszy przepływ powietrza",5,12,349)

INSERT INTO "product"("name","description","category","brand","price")
VALUES ("Zalman i3 series LD edge z oknem","Zapewnij swojemu desktopowi niepowtarzalną oprawę z czarną obudową Zalman i3 Edge. Ta oryginalna konstrukcja wyróżnia się minimalistycznym gamingowym designem, uzupełnionym przez akrylowe panele oraz podświetlenie LED. Możesz z dumą wyeksponować stworzoną konfigurację i ukazać desktopa skąpanego w jednokolorowym blasku. Przestronne wnętrze pomieści wszystkie niezbędne komponenty, a fabryczny system chłodzenia zapewni odpowiednią wentylację.",5,13,209)


#ShipmentType

INSERT INTO "shipment_type"("name","description","price") VALUES ("kurier","Usługa kurierska DPD",15)
INSERT INTO "shipment_type"("name","description","price") VALUES ("odbiór osobisty","Odbiór osobisty w siedzibie firmy",0)
INSERT INTO "shipment_type"("name","description","price") VALUES ("inpost","paczkomaty",8.99)

#Payment_type

INSERT INTO "payment_type"("name","description") VALUES ("paypal","Płatność za pośrednictwem serwisu Paypal")
INSERT INTO "payment_type"("name","description") VALUES ("przelew bankowy","Płatność przy pomocy konta bankowego")
INSERT INTO "payment_type"("name","description") VALUES ("przelewy24.pl","Płatność za pośrednictwem serwisu przelewy24.pl")

# --- !Downs

#CATEGORY

DELETE FROM "category" WHERE name="procesory";
DELETE FROM "category" WHERE name="karty graficzne";
DELETE FROM "category" WHERE name="pamięć RAM";
DELETE FROM "category" WHERE name="dyski twarde";
DELETE FROM "category" WHERE name="obudowy";


#BRAND

DELETE FROM "brand" WHERE name="intel";
DELETE FROM "brand" WHERE name="AMD";