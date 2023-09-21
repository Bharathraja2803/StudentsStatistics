package dev;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStreamFinalChallenge {

	public static void main(String[] args) {
        
		/*
		 * Before you start, first change the getRandomStudent method on Student, 
		 	to select a random number and random selection of courses. -> Done  
		 * Every student should be enrolled, and have activity in at least one class. -> Done
		 * Set up three or four courses, use the lecture count version of the constructor on several of these, 
		 	to pass lecture counts greater than 40. -> done
		 * Generate a list of 10,000 students, who've enrolled in the past 4 years. -> Done
		 * Pass the supplier code three or four courses. -> done
			
			========================================================
			Next, answer the following questions.
				1. How many of the students are enrolled in each class?
				2. How many students are taking 1, 2, or 3 classes?
				3. Determine the average percentage complete, 
					for all courses, for this group of students.  
						Hint, try Collectors.averagingDouble to get this information.
				4. For each course, get activity counts by year, using the last activity year field.
				5. Think about how you'd go about answering these questions, 
					using some of the stream operations you've learned, 
						especially the collect terminal operation in conjunction, 
							with the Collectors helper class methods.

			========================================================
		 */
		Course pymc= new Course("PYMC", "Python Masterclass",50);
        Course jmc= new Course("JMC", "Java Masterclass",100);
        Course jGame = new Course("JGAME","Creating Games in Java");
        Course astd = new Course("ASTD", "Android Studio App development", 60);
        
        List<String> courseList = new ArrayList<>(List.of("PYMC","JMC","JGAME","ASTD"));
        
        List<Student> studentsList = 
        		Stream.generate(() -> Student.getRandomStudent(pymc,jmc,jGame, astd))
        		.limit(10_000)
        		.toList();
        
        
        String q1 = "1. How many of the students are enrolled in each class?";
        System.out.printf("%s%n%s%n%s :%n",q1, "=".repeat(q1.length()),"Answer");
        System.out.println("My version");
        var courseGroupWiseCount = studentsList.stream()
        		.collect(Collectors.groupingBy(s -> s.getCourses().toString(),Collectors.counting()))
        		
        		;
        
        courseGroupWiseCount.forEach((k,v)-> System.out.println(k+" : "+v));
        
        System.out.println("=".repeat(80));
        for(String cc : courseList) {
        	var courseWiseCount = studentsList.stream()
        			.filter(s -> s.getCourses().contains(cc))
            		.count();
				
        	System.out.println(cc+" : "+ courseWiseCount);
        }
        System.out.println("=".repeat(80));
        System.out.println("Bob's version");
        var courseGroupWiseCount1 = studentsList.stream()
        		.flatMap(s -> s.getEngagementMap().values().stream())
        		.collect(Collectors.groupingBy(s -> s.getCourseCode(),Collectors.counting()))
        		
        		;
        
        courseGroupWiseCount1.forEach((k,v)-> System.out.println(k+" : "+v));
        
        System.out.println("=".repeat(80));
       
        
        
        
        String q2 = "2. How many students are taking 1, 2, or 3 classes?";
        var noOfCourseWiseCount = studentsList.stream()
        		.collect(Collectors.groupingBy(s -> {
        			int noOfCourse = s.getCourses().size();
        			return switch(noOfCourse) {
        			case 1 -> "one course";
        			case 2 -> "two course";
        			case 3 -> "three course";
        			case 4 -> "four courses";
        			default -> "no course";
        			};
        		},Collectors.counting()));
        
        System.out.printf("%s%n%s%n%s :%n",q2, "=".repeat(q2.length()),"Answer");
        noOfCourseWiseCount.forEach((k,v) -> System.out.println(k+" : "+v));
        System.out.println("=".repeat(80));
        
        String q3 = "3. Determine the average percentage complete, for all courses, for this group of students. Hint, try Collectors.averagingDouble to get this information.";
        System.out.printf("%s%n%s%n%s :%n",q3, "=".repeat(q3.length()),"Answer");
        for (String cc : courseList) {
        	var avgPercentCourseCompletion = studentsList.stream()
            		.filter(s -> s.getCourses().contains(cc))
            		.collect(Collectors.averagingDouble(s -> s.getPercentComplete(cc)))
            		;
        	System.out.println(cc+" : "+avgPercentCourseCompletion);
        }
        System.out.println("=".repeat(80));
        String q4 = "4. For each course, get activity counts by year, using the last activity year field.";
        System.out.printf("%s%n%s%n%s :%n",q4, "=".repeat(q4.length()),"Answer");
        var activityWiseCount = studentsList.stream()
        		.collect(Collectors.groupingBy(s -> LocalDate.now().getYear() - s.getMonthsSinceActive()/12 , Collectors.counting()));
        
        activityWiseCount.forEach((k,v)-> System.out.println(k+" : "+v));
        System.out.println("=".repeat(80));
	}

}
