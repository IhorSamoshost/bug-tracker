 # MiniProject 1: Bug-tracker

 Example repo: https://github.com/lazermann111/InternetShop  
 Example of bug-tracker: https://bugs.openjdk.java.net/issues/?filter=-4

 3-tier architecture

 Ticket required attributes attributes:
  1. Name
  2. Description
  3. Assignee
  4. Reporter
  5. Status
  6. Priority
  7. Time spent
  8. Time estimated
 
 View layer: Console IO
  
 Menu list:  
  Login menu:   
   • Login/Register user 
             
  User menus:     
   1. Create ticket 
   2. Edit ticket (log time, change status, description etc) 
   3. My tickets list 
   4. \* Dashboard page, example of dashboards   
      a. Total time estimated by users   
      b. Most time-expensive tasks   
      c. Make research on actual trackers and made up your own dashboards   
      Dashboards here are just formation text information 
   5. *** Implement your own  JQL for advanced search, start with this: 
          https://www.atlassian.com/blog/jira-software/jql-the-most-flexible-way-to-search-jira-14 
			 
 Services layer - business logic, validators  etc 

 Dao layer: In-mem collection, or *in-mem DB (H2), 
 you can start with pre-populated users/products in DB 

   \* - are optional (additional score) 

 Links: 
 https://www.jinfonet.com/resources/bi-defined/3-tier-architecture-complete-overview/ 
 https://habr.com/ru/post/276593/ 
