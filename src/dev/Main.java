package dev;

import java.nio.file.DirectoryStream.Filter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) {

        Course pymc= new Course("PYMC", "Python Masterclass");
        Course jmc= new Course("JMC", "Java Masterclass");

        Student[] studentArray = new Student[1000];
        Arrays.setAll(studentArray, i -> Student.getRandomStudent(jmc, pymc));
       
        System.out.println("=".repeat(80));
        		var genderCounting = Arrays.stream(studentArray)
                .collect(Collectors.groupingBy(Student::getGender,Collectors.counting()))
                ;
        
        genderCounting.forEach((k, v) -> System.out.println(k+":"+v));
        
        System.out.println("=".repeat(80));
        var ageListing = Arrays.stream(studentArray)
                .collect(Collectors.groupingBy(s -> {
                	if(s.getAge() < 30) {
                		return "Less Than 30";
                	}else if(s.getAge() >=30 && s.getAge() <= 60){
                		return "Between 30 and 60";
                	}else {
                		return "Over 60 years";
                	}
                	},Collectors.counting()))
                ;
        
        ageListing.forEach((k,v) -> System.out.println(k+": "+v));
        
        System.out.println("=".repeat(80));
        System.out.println("Distinct Countries of studnets entrolled: ");
        Arrays.stream(studentArray)
                .map(Student::getCountryCode)
                .distinct()
                .forEach(System.out::println);
        
        System.out.println("=".repeat(80));
        var ageStatistics = Stream.generate(() -> Student.getRandomStudent(jmc,pymc))
        		.limit(20)
        		.mapToInt(Student::getAge)
        		.summaryStatistics()
        		;
        System.out.println("Age Statistics: "+ageStatistics);
       
        System.out.println("=".repeat(80));
        var stillActive = Arrays.stream(studentArray)
         		.filter(s -> s.getYearEnrolled() <= 2016) 
         		//.peek(System.out::println)
         		.anyMatch(s ->  s.getEngagementMap().values().stream()
         				//.peek(System.out::println)
         		.anyMatch(c -> c.getLastActivityYear() == LocalDate.now().getYear()))
         		;
         
         System.out.println("Enrolled 7 years and still active : "+stillActive);
        
        System.out.println("=".repeat(80));
        System.out.println("Top 5 Student who enrolled 6 years back and learning till now ");
        Arrays.stream(studentArray)
        		.limit(100)
        		.filter(s -> s.getYearEnrolled() <= 2015)
//        		.filter( c -> c.getEngagementMap().values().stream().anyMatch(v-> v.getLastActivityYear() == LocalDate.now().getYear()))
        		.filter(s -> s.getEngagementMap()
        						.values()
        						.stream()
        						.anyMatch(c -> c.getLastActivityYear()==LocalDate.now().getYear())
        						)
        		
        		.limit(5)
        		.forEach(System.out::println);
					
       
        

                
        
    }
}
