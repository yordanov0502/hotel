<div align="center">
  <a href="https://github.com/yordanov0502/hotel">
    <img src="documentation\logo.jpg" alt="Logo" height="250">
  </a>

  <p align="center">
    Курсов проект по ООП-2 2022/2023
    <br />
    <a href="https://github.com/yordanov0502/hotel/issues">Report Bug</a>
    ·
    <a href="https://github.com/yordanov0502/hotel/issues">Request Feature</a>
  </p>
</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Съдържание</summary>
  <ol>
    <li><a href="#За-проекта">За проекта</a></li>
    <li><a href="#Задание-на-проекта">Задание на проекта</a></li>
    <li><a href="#Версии-на-програмата">Версии</a></li>
  </ol>
</details>

![image1.png](documentation%2Fimage1.png)
![image2.png](documentation%2Fimage2.png)
![image3.png](documentation%2Fimage3.png)
![image4.png](documentation%2Fimage4.png)
![image5.png](documentation%2Fimage5.png)

## За проекта

Курсов проект по Обектно ориентирано програмиране част 2 за Технически университет Варна.

Програма съхраняваща и обработваща информация и данни за хотелиерски услуги.

<a href="https://github.com/yordanov0502/hotel/graphs/contributors">Създаден от Тодор Йорданов и Здравко Иванов.</a>

## Задание на проекта

Да се разработи информационна система – Хотел.  
Програмата съхранява и обработва данни за хотелски услуги (резервации и допълнителни услуги). Системата позволява множествен достъп.

Системата поддържа два вида потребители администратор и клиенти (рецепционист, мениджър, собственик) с различни роли за достъп до функционалностите в системата.

Операции за работа с потребители:  
• Създаване на собственици на хотел(и) от администратор;  
• Създаване на хотел с мениджър от собственик  
• Създаване на рецепционисти от мениджър

Системата поддържа операции за работа с резервации:  
• Създаване на клиенти;  
• Създаване на резервация от рецепционист (Номер на резервация, Тип на
резервация, Тип на прекратяване на резервацията, категории стаи, ...);  
• Създаване и предоставяне на допълнителни услуги, съобразени със сезона
(отчетност на тип услуга и брой ползвания);  
• Рейтинговане на клиенти.

Системата поддържа справки по произволен период за:

• Категория клиенти:    
o Информация за клиенти(лични данни);  
o Използване на хотел и хотелски услуги.   
o Рейтинг на клиенти

• Рецепционисти (създадени резервации, данните на рецепциониста);  
• Създадени регистрации (дата, статус, хотел, съдържание на формуляра);  
• Стаи (Рейтинг на стаите за ползваемост);  
Мениджър на хотел достъпва справки само за хотел, за който е отговорен. Собственика достъпва справки за всички притежаващи хотели. Рецепциониста има право на справки за заетостта на стаите.

Системата поддържа известия за събития:  
• Изтичаща резервация;  
• Известия за рисков клиент (при създаване на нова резервация).

## Версии на програмата

- [x] version 3.7.6  
  Актуализиране на .exe файл и документация

- [x] version 3.7.5  
  Добавяне на Unit тестове

- [x] version 3.7.4  
  Премахване на грешка относно ретинговането на стаи

- [x] version 3.7.3  
  Актуализация на графичния интерфейс (на таблици)

- [x] version 3.7.2  
  Добавяне на документация и актуализирана презентация

- [x] version 3.7.1  
  Добавяне на справка за клиенти към собственик

- [x] version 3.7.0  
  Добавяне на справка за резервациите на рецепционисти към собственик

- [x] version 3.6.9  
  Добавяне на справка за създадени регистрации към собственик

- [x] version 3.6.8  
  Добавяне на справка за рейтинг на стаи към собственик

- [x] version 3.6.7  
  Актуализиране на README файл

- [x] version 3.6.6  
  Добавяне на справка за клиенти към мениджър


- [x] version 3.6.5  
  Справка за резервации на даден рецепционист добавена към мениджър


- [x] version 3.6.4  
  Справка за резервации добавена към мениджър


- [x] version 3.6.3  
  Справка за рейтинг на стаите добавена към мениджър


- [x] version 3.6.2  
  Добавяне на информация за хотел към мениджър


- [x] version 3.6.1  
  Създаване на нотификации за резервациите + добавяне на часовник


- [x] version 3.6.0  
  Добавяне на приключване на резервация


- [x] version 3.5.1  
  Добавяне на ReceptionistHotelInfo


- [x] version 3.5.0  
  Премахване на OwnerAddService


- [x] version 3.4.9  
  Модифициране на CreateReservation функционалността


- [x] version 3.4.8  
  Добавяне на CreateReservation


- [x] version 3.4.7  
  Модифициране на AddNewReservationView


- [x] version 3.4.6  
  Създване на AddNewReservationView


- [x] version 3.4.5  
  Създаване на ReservationService


- [x] version 3.4.3  
  Създаване на ReservationRepository


- [x] version 3.4.2  
  Създаване на модел относно резервациите


- [x] version 3.4.1  
  Създаване на Reservation entity


- [x] version 3.4.0  
  Оправяне на бъг свързан с валидациите относно стаите


- [x] version 3.3.9  
  Модификация на обектите за стаи и клиенти (Room and Customer entities)


- [x] version 3.3.8  
  Добавяне на нова колона относно обекта за стаи (Room entity)


- [x] version 3.3.7  
  Добавяне на нова колона относно обекта за стаи (Room entity)


- [x] version 3.3.6  
  Оправяне на бъг в системата


- [x] version 3.3.5  
  Създаване на ReceptionistAddServiceView


- [x] version 3.3.4  
  Добавяне на ReceptionistAddCustomerView


- [x] version 3.3.3  
  Добавяне на ReceptionistMainView + submenu


- [x] version 3.3.2  
  Добаявне на AddNewReceptionistView


- [x] version 3.3.1  
  Създаване на ManagerMainView + sub-menu


- [x] version 3.3.0  
  Добавяне на OwnerHotelsInfoView + други актуализации


- [x] version 3.2.1  
  Актуализиране на метода за добавяне на стаи


- [x] version 3.2.0  
  Създаване на нов хотел + нов мениджър


- [x] version 3.1.3  
  Добавяне на OwnerHotelServicesInformationController


- [x] version 3.1.2  
  Актуализация на OwnerHotelRoomsInformationController


- [x] version 3.1.1  
  Поправяне на малки бъгове по програмата


- [x] version 3.1.0  
  Създаване на обекти за допълнителни услуги(Service entity) + актуализация на OwnerAddHotel


- [x] version 3.0.8  
  Добавяне на AddNewHotelAndNewManagerView


- [x] version 3.0.7  
  Добавяне на AddHotelAndManagerView


- [x] version 3.0.6  
  Модификация на UserRepositoryImpl


- [x] version 3.0.5  
  Актуализиране на HotelService


- [x] version 3.0.4  
  Добавяне на обекти за Стаи (Room entity)


- [x] version 3.0.3  
  Премахване на ненужните класове на обекти


- [x] version 3.0.2  
  Актуализиране на Админското sub-menu


- [x] version 3.0.1  
  Оптимизиране на sub-menu + създаване на OwnerMainView


- [x] version 3.0.0  
  Приключване на Админските функционалности


- [x] version 2.3.3  
  Добавяне на обекти относно HotelsUsers


- [x] version 2.3.2  
  Актуализиране на Hotel Service


- [x] version 2.3.1  
  Модифициране на Hotel entity


- [x] version 2.3.0  
  Създаване на обекти относно хотелите в системата (Hotel entity)


- [x] version 2.2.9  
  Добавяне на рестрикции относно Админите в програмта


- [x] version 2.2.8  
  Актуализиране на Admins Views


- [x] version 2.2.7  
  Показване на ReceptionistsInformation


- [x] version 2.2.6  
  Показване на ManagersInformation


- [x] version 2.2.5  
  Показване на OwnersInformation


- [x] version 2.2.4  
  Модифициране на бутона за затваряне на програмата


- [x] version 2.2.3  
  Актуализация на AdminAddOwnerController


- [x] version 2.2.2  
  Създаване на AdminAddOwner.fxml


- [x] version 2.2.1  
  Актуализация на AdminMain.fxml


- [x] version 2.2.0  
  Създаване на ново sub-menu + модифициране на Admin контролерите


- [x] version 2.1.1  
  Актуализиране на UserService, UserRepository и UserLoginController


- [x] version 2.1.0    
  Модифициране на UserRegistrationController и UserService


- [x] version 2.0.6  
  Добавяне на AlertManager и различни оптимизации


- [x] version 2.0.5  
  Създаване на UserServiceTest


- [x] version 2.0.4  
  Актуализация на UserService


- [x] version 2.0.3  
  Добавяне на Hasher и актуализация на UserService


- [x] version 2.0.2  
  Добавяне на UserService и UserModel + актуализация на репото


- [x] version 2.0.1  
  Актуализиране на потребителските обекти


- [x] version 2.0.0  
  Пълно преструктуриране на проекта и имплементиране на потребителските обекти


- [x] version 1.2.0  
  Добавяне на Hibernate


- [x] version 1.1.2  
  премахване на ненужните loj4f стойности и връзки


- [x] version 1.1.1  
  Промяна на Adminregistration.fxml


- [x] version 1.1.0  
  Добавяне на лог log4j и променяне на директорията на Main сцената


- [x] version 1.0.3  
  Създаване на тестова директория и тестови клас


- [x] version 1.0.2  
  Създаване на utility клас Constants.java


- [x] version 1.0.1  
  Дефиниране на CommonTask.java като utility клас


- [x] version 1.0.0(welcome + login pages)  
  Създаване на отделни пакети и разделяне на проекта на части;  
  Проектиране на Welcome View;  
  Добавяне на log in страници за админ, собственик, мениджър и рецепционист.
