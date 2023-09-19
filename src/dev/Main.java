package dev;

import java.nio.file.DirectoryStream.Filter;
import java.time.LocalDate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) {

        Course pymc= new Course("PYMC", "Python Masterclass");
        Course jmc= new Course("JMC", "Java Masterclass");
//        Student tim = new Student("AU", 2019, 30, "M",
//                true, jmc, pymc);
//        System.out.println(tim);
//
//        tim.watchLecture("JMC", 10, 5, 2019);
//        tim.watchLecture("PYMC", 7, 7, 2020);
//        System.out.println(tim);
        System.out.println("=".repeat(80));
        var genderCounting = Stream.generate(() -> Student.getRandomStudent(jmc, pymc))
                .limit(20)
                .collect(Collectors.groupingBy(Student::getGender,Collectors.counting()))
                ;
        
        genderCounting.forEach((k, v) -> System.out.println(k+":"+v));
        
        System.out.println("=".repeat(80));
        var ageListing = Stream.generate(() -> Student.getRandomStudent(jmc, pymc))
                .limit(20)
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
        Stream.generate(() -> Student.getRandomStudent(jmc, pymc))
                .limit(20)
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
        var stillActive = Stream.generate(() -> Student.getRandomStudent(jmc,pymc))
         		.limit(20)
         		.filter(s -> s.getYearEnrolled() <= 2015) 
         		//.peek(System.out::println)
         		.anyMatch(s ->  s.getEngagementMap().values().stream()
         				//.peek(System.out::println)
         		.anyMatch(c -> c.getLastActivityYear() == LocalDate.now().getYear()))
         		;
         
         System.out.println("Enrolled 7 years and still active : "+stillActive);
        
        System.out.println("=".repeat(80));
        System.out.println("Top 5 Student who enrolled 6 years back and learning till now ");
        Stream.generate(() -> Student.getRandomStudent(jmc,pymc))
        		.limit(100)
        		.filter(s -> s.getYearEnrolled() <= 2015)
//        		.filter( c -> c.getEngagementMap().values().stream().anyMatch(v-> v.getLastActivityYear() == LocalDate.now().getYear()))
        		.filter(s -> s.getEngagementMap()
        						.values()
        						.stream()
        						.anyMatch(c -> c.getLastActivityYear()==2023)
        						)
        		
        		.limit(5)
        		.forEach(System.out::println);
					
       
        
//        var maleStream = allStudentStream
                //.forEach(System.out::println);
                //.filter(s -> s.getGender() == "M")
                //.collect(Collectors.groupingBy(Student::getGender))
//                .filter(s -> s.getGender() == "M");
            
       //var femaleStream = allStudentStream.filter(s -> s.getGender() == "F");
          
         //  System.out.println("Male Students Count: "+maleStream.count());
           //System.out.println("Female Students Count: "+femaleStream.count());
                
        
    }
}
