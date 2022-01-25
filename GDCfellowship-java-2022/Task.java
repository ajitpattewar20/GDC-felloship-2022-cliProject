//|//////////////////////////////////////////////////////////////////////////////////////////|
//|/////////////////////Submiting to GDC fellowship//////////////////////////////////////////|
//|=============================submited by==================================================|
//|-----------------------Ajit Ganesh Pattewar-----------------------------------------------|
//|----------------------ajitpattewar20@gmail.com-------------------------------------------/|
//|//////////////////////////////////////////////////////////////////////////////////////////|

import java.io.*;
import java.util.*;

public class Task {

//creating task oject---------------------------------------------------
	String priority;
	String Taskname;

	Task(String priority, String Taskname) {
		this.priority = priority;
		this.Taskname = Taskname;
	}
//----------------------------------------------------------------------

//getter and setter-----------------------------------------------------  
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTaskname() {
		return Taskname;
	}

	public void setTaskname(String Taskname) {
		this.Taskname = Taskname;
	}
//---------------------------------------------------------------------------------------

//sorting method------------------------------------------------------------------------
	public static Comparator<Task> sortTask = new Comparator<Task>() {
		public int compare(Task t1, Task t2) {
			int priority1 = Integer.parseInt(t1.getPriority());
			int priority2 = Integer.parseInt(t2.getPriority());

			// For ascending order
			return priority1 - priority2;

			// For descending order
			// rollno2-rollno1;
		}
	};
//------------------------------------------------------------------------

//tostring for printing--------------------------------------------------
	@Override
	public String toString() {
		// return "[ priority= " + priority + ", Taskname= "+ Taskname + "]";
		return priority + " " + Taskname;
	}
//-----------------------------------------------------------------------------

//write to txt file---------------------------------------------------------------------- 
	public static void writeTaskTofile(String filename, String prior, String task, boolean append) throws IOException {
		// creating the file
		File writeFile = new File(filename);
		// 2 create a file writer class
		FileWriter fw = new FileWriter(writeFile, append);

		// 3create a print writer class
		PrintWriter pw = new PrintWriter(fw);

		String printingtext = prior + " " + task;
		pw.println(printingtext);
		pw.close();
	}

	/// writing to completed.txt/////////////////
	public static void writeTaskToCfile(String filename, String task, boolean append) throws IOException {
		// creating the file
		File writeFile = new File(filename);
		// 2 create a file writer class
		FileWriter fw = new FileWriter(writeFile, append);

		// 3create a print writer class
		PrintWriter pw = new PrintWriter(fw);

		String printingtext = task;
		pw.println(printingtext);
		pw.close();
	}
//---------------------------------------------------------------------------------------

//Read from txt file---------------------------------------------------------------------- 
	public static ArrayList<Task> readTaskFromFile(String filename) throws FileNotFoundException {
		File readfile = new File(filename);
		Scanner sc = new Scanner(readfile);

		ArrayList<Task> arrRTaskList = new ArrayList<Task>();

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] items = line.split(" ", 2);

			// put all of these items into Task object
			String rpriority = items[0];
			String rTaskname = items[1];

			Task newTask = new Task(rpriority, rTaskname);
			arrRTaskList.add(newTask);
		}
		sc.close();
		Collections.sort(arrRTaskList, Task.sortTask);
		return arrRTaskList;
	}

	/// reading from completed.txt//////////////////////
	public static ArrayList<String> readTaskFromCFile(String filename) throws FileNotFoundException {
		File readfile = new File(filename);
		Scanner sc = new Scanner(readfile);

		ArrayList<String> arrRTaskCList = new ArrayList<String>();

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String items = line;

			String rTaskname = items;

			String newTask = rTaskname;
			arrRTaskCList.add(newTask);
		}
		sc.close();
		return arrRTaskCList;
	}
//----------------------------------------------------------------------------------------

//main method-----------------------------------------------------------------------------
	public static void main(String args[]) throws IOException {
		File taskf = new File("task.txt");
		File completedf = new File("completed.txt");
		ArrayList<Task> rtal = new ArrayList<Task>();
		ArrayList<String> rtalc = new ArrayList<String>();
		if (taskf.exists() && completedf.exists()) {
//			System.out.println("The files exists.");
		} else {
//			System.out.println("The file does not exist.");
			try {
				if (taskf.createNewFile() || completedf.createNewFile()) {
//						  System.out.println("File created: " + taskf.getName()+"&"+completedf.getName());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

//input***********************************************************************
		String asdf[] = {};

		if (args.length == 0) {
			String asdf0[] = { "" };
			asdf = asdf0;
		} else if (args.length == 1) {
			String asdf1[] = { args[0] };
			asdf = asdf1;
		} else if (args.length == 2) {
			String asdf2[] = { args[0], args[1] };
			asdf = asdf2;
		} else if (args.length == 3) {
			String asdf3[] = { args[0], args[1], args[2] };
			asdf = asdf3;
		}

//p Task in Task.txt | Task in completed.txt**********************************

		// check the number of arguments
		// if less than 1 and =to help print help info
		if (asdf.length < 1 || asdf[0].equals("help")) {
			System.out.println("Usage :-\n"
					+ "$ ./task add 2 hello world    # Add a new item with priority 2 and text \"hello world\" to the list\n"
					+ "$ ./task ls                   # Show incomplete priority list items sorted by priority in ascending order\n"
					+ "$ ./task del INDEX            # Delete the incomplete item with the given index\n"
					+ "$ ./task done INDEX           # Mark the incomplete item with the given index as complete\n"
					+ "$ ./task help                 # Show usage\n" + "$ ./task report               # Statistics");
		} else if (asdf.length >= 1 && !asdf[0].equals("help")) {
			switch (asdf[0]) {
			case "add":
				if (asdf.length == 3) {

//			    System.out.println("adding");		    

					writeTaskTofile("task.txt", asdf[1], asdf[2], true);

//			    System.out.println("Added task: "+asdf[2]+" with priority "+asdf[1]);
					System.out.println("Added task: \"" + asdf[2] + "\" with priority " + asdf[1] + "");
				} else {
					System.out.println("Error: Missing tasks string. Nothing added!");
				}
				break;
			case "ls":
				if (asdf.length == 1) {
//			    System.out.println("showing by ls");
					// rtal- read task array list
					rtal = readTaskFromFile("task.txt");

					// required==== 1. task [2]
					if (rtal.size() > 0) {
						for (int i = 0; i < rtal.size(); i++) {
							System.out.print(
									(i + 1) + ". " + rtal.get(i).Taskname + " [" + rtal.get(i).priority + "]" + "\n");
						}
					} else {
						System.out.println("There are no pending tasks!");
					}
				}
				break;
			case "del":
				if (asdf.length == 2) {
					if (Integer.parseInt(asdf[1]) == 0) {
						//System.out.println("Error: no incomplete item with index #0 exists");
					//	System.out.println("Error: task with index #" + asdf[1] + " does not exist. Nothing deleted"+"");
						System.out.print("Error: task with index #0 does not exist. Nothing deleted.");
						
					} else {
//					    System.out.println("deleting task");
							rtal = readTaskFromFile("task.txt");

							if (Integer.parseInt(asdf[1]) <= rtal.size()) {
								rtal.remove(Integer.parseInt(asdf[1]) - 1);

								boolean tf = false;
								for (int i = 0; i < rtal.size(); i++) {
//								System.out.println((i+1)+". "+rtal.get(i).Taskname+" ["+rtal.get(i).priority+"]");

									writeTaskTofile("Task.txt", rtal.get(i).priority, rtal.get(i).Taskname, tf);
									tf = true;
								}
//						    System.out.println("Deleted item with index "+asdf[1]);
								System.out.println("Deleted task #" + asdf[1]);
							} else {
								// System.out.println("Error: item with index "+asdf[1]+" does not exist.
								// Nothing deleted.");
								System.out.print("Error: task with index #" + asdf[1] + " does not exist. Nothing deleted"+".");
							}
					}
				} else {
					System.out.println("Error: Missing NUMBER for deleting tasks.");
				}

				break;
			case "done":
				if (asdf.length == 2) {
					if (Integer.parseInt(asdf[1]) == 0) {
						System.out.print("Error: no incomplete item with index #0 exists"+".");
					} else {
//					    System.out.println("done a task");
							rtal = readTaskFromFile("task.txt");

							if (Integer.parseInt(asdf[1]) <= rtal.size()) {
								// printing in completed.txt
								String tname = rtal.get(Integer.parseInt(asdf[1]) - 1).Taskname;

								writeTaskToCfile("completed.txt", tname, true);

								rtal.remove(Integer.parseInt(asdf[1]) - 1);
								boolean tf = false;
								for (int i = 0; i < rtal.size(); i++) {
//								System.out.println((i+1)+". "+rtal.get(i).Taskname+" ["+rtal.get(i).priority+"]");

									writeTaskTofile("Task.txt", rtal.get(i).priority, rtal.get(i).Taskname, tf);
									tf = true;
								}
								System.out.println("Marked item as done.");
							} else {
								System.out.print("Error: no incomplete item with index " + asdf[1] + " exists"+".");
							}
						
					}
				}else {
					System.out.print("Error: Missing NUMBER for marking tasks as done.");
				}
				break;
			case "report":
				if (asdf.length == 1) {
//				    System.out.println("reporting");
					rtal = readTaskFromFile("task.txt");
					rtalc = readTaskFromCFile("completed.txt");
					//System.out.print("Pending : 1\n1. water the plants [1]\n\nCompleted : 2\n1. the thing i need to do\n2. find needle in the haystack\n");
					// printing pending task
					String stringbuilder="";
					stringbuilder+="Pending : " + rtal.size()+"\n";
					//System.out.print("Pending : " + rtal.size());// count of pending task
					for (int i = 0; i < rtal.size(); i++) {
						
						//System.out.print("\n"+(i + 1) + ". " + rtal.get(i).Taskname + " [" + rtal.get(i).priority + "]"+".");
						stringbuilder+=(i + 1) + ". " + rtal.get(i).Taskname + " [" + rtal.get(i).priority + "]"+"\n\n";
					}

					// printing completed task
					stringbuilder+="Completed : " + rtalc.size();
					//System.out.print("\n"+"Completed : " + rtalc.size() + "\n");// count of completed task
					for (int i = 0; i < rtalc.size(); i++) {
						//System.out.print((i + 1) + ". " + rtalc.get(i) + "\n");
						stringbuilder+="\n";
						stringbuilder+=(i + 1) + ". " + rtalc.get(i);
					}
				System.out.print(stringbuilder+"\n");
				}
				break;
			default:
				System.out.println("Usage :-\n"
						+ "$ ./task add 2 hello world    # Add a new item with priority 2 and text \"hello world\" to the list\n"
						+ "$ ./task ls                   # Show incomplete priority list items sorted by priority in ascending order\n"
						+ "$ ./task del INDEX            # Delete the incomplete item with the given index\n"
						+ "$ ./task done INDEX           # Mark the incomplete item with the given index as complete\n"
						+ "$ ./task help                 # Show usage\n"
						+ "$ ./task report               # Statistics");
				break;
			}
		}
	}
//-----------------------------------------------------------------------------
}
