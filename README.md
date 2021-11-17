# taskmaster

## Lab 26 - Beginning TaskMaster

### Feature Tasks :

1- **Homepage :**
The main page should have a heading at the top of the page, an image to mock the “my tasks” view, and buttons at the bottom of the page to allow going to the “add tasks” and “all tasks” page.

[![page1.jpg](https://i.postimg.cc/7hpWQTWH/page1.jpg)](https://postimg.cc/XBkLrXWm)

2- **Add a Task page :**
On the “Add a Task” page, allow users to type in details about a new task, specifically a title and a body. When users click the “submit” button, show a “submitted!” label on the page.

[![page2.jpg](https://i.postimg.cc/Cxp2Gk8J/page2.jpg)](https://postimg.cc/nCTTZs0Q)

3- **All Tasks page :**
The all tasks page should just be an image with a back button, it needs no functionality.

[![page3.jpg](https://i.postimg.cc/W42fKVXk/page3.jpg)](https://postimg.cc/qN5LhPfJ)

---

## Lab 27 - Adding Data to TaskMaster

### Feature Tasks :

+ Task Detail Page : It should have a title at the top of the page, and a Lorem Ipsum description.

[![task1.jpg](https://i.postimg.cc/2ywZDWbr/task1.jpg)](https://postimg.cc/JHsn3G52)

[![task2.jpg](https://i.postimg.cc/rwztBK82/task2.jpg)](https://postimg.cc/bZcJbYDm)

[![task3.jpg](https://i.postimg.cc/qRHh6w2p/task3.jpg)](https://postimg.cc/KKQvdn9W)

+ Settings Page : It should allow users to enter their username and hit save.

[![setting.jpg](https://i.postimg.cc/LscfB2FK/setting.jpg)](https://postimg.cc/bGRsqXv3)

+ Homepage : should be modified to contain three buttons with titles. When a user taps one of the titles, it should go to the Task Detail page, and the title at the top of the page should match the task title that was tapped on the previous page.

+ The homepage:  should also contain a button to visit the Settings page, and once the user has entered their username, it should display “{username}’s tasks” above the three task buttons.

[![home.jpg](https://i.postimg.cc/BbxC1tjV/home.jpg)](https://postimg.cc/5Q2zh4qB)

---

## Lab 28 - RecyclerViews for Displaying Lists of Data

### Feature Tasks :

1- Task Model : create a Task class, have a title, a body, and a state. The state should be one of “new”, “assigned”, “in progress”, or “complete”.

2- Homepage : refactor your homepage to use a RecyclerView for displaying Task data. 

+ Ensure that you can tap on any one of the Tasks in the RecyclerView, and it will appropriately launch the detail page with the correct Task title displayed.

[![recycler-View-Item1.jpg](https://i.postimg.cc/LsFDK3k8/recycler-View-Item1.jpg)](https://postimg.cc/DS5L1sY9)

[![Recycler-View-Item1-Onclick.jpg](https://i.postimg.cc/QMqQLGxf/Recycler-View-Item1-Onclick.jpg)](https://postimg.cc/7CfG7Rzz)

[![Recycler-Viewsitem2.jpg](https://i.postimg.cc/j5dztgDt/Recycler-Viewsitem2.jpg)](https://postimg.cc/PL9LMMbR)

[![Recycler-Views-Item2-Onclick.jpg](https://i.postimg.cc/C5RkfRC2/Recycler-Views-Item2-Onclick.jpg)](https://postimg.cc/JHWGf423)

--- 

# Lab: 29 - Room

## Feature Tasks :

1- Add Task Form : modify Add Task form to save the data entered in as a Task in the local database.

[![addTask.jpg](https://i.postimg.cc/fWtvVy25/addTask.jpg)](https://postimg.cc/HjmMKYC7)

2- Homepage : Refactor your homepage’s RecyclerView to display all Task entities in your database.

[![newTask.jpg](https://i.postimg.cc/Pr1zMckw/newTask.jpg)](https://postimg.cc/bsNtymry)

3- Detail Page : Ensure that the description and status of a tapped task are also displayed on the detail page, in addition to the title. 

[![details.jpg](https://i.postimg.cc/mgwYvHkZ/details.jpg)](https://postimg.cc/JssHJtjS)