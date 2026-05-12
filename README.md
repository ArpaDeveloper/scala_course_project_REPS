# REPS Project
Our implementation of REPS (Renewable Energy Plant System) has these
functionalities. Link to codebase:

• 1.Monitor and Control resources (Get from API)

• 2.Collect Data (Save to File)

• 3.View energy generation and storage capacity (Show file)

• 4.Data Analysis

• 5.Close program

Option 1 allows the user to get data from Fingrid API. Users can choose to get data from
Wind, Water, or Solar Power. Users can also select the time range for the data. Options
are hourly, daily, weekly, or monthly data. 

Option 2 allows users to save data from the renewable energy sources to file.txt.

Option 3 allows users to view energy generation and storage capacity by loading file.txt
data.

Option 4 allows users to perform data analysis on the data (List of energy values from
the API). Options for data analysis include mean, mode, range, midrange, and median.

Option 5 closes the program.


## How to Run the Project

1. Clone the repository from GitHub.

2. Go to Build.sbt shell & run commands (in order).
   
       Reload
   
       Update
   
       Compile
   *Loads all necessary depencies*

3. Use your own fingridAPI key.

   *Add it to Data.Scala file*

4. Run Main.scala file in the editor.
   
   *Starts the program. UI is in terminal.*

### Common errors
> [!NOTE]
> Sometimes you need to restart the editor after loading depencies for it to start working!
