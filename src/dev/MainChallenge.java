package dev;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainChallenge {
	public static void main(String[] args) {
        Course pymc= new Course("PYMC", "Python Masterclass",50);
        Course jmc= new Course("JMC", "Java Masterclass",100);
        Course cgj= new Course("CGJ","Creating Games in Java");
        
        List<Student> studentsList = 
        		Stream.generate(() -> Student.getRandomStudent(pymc,jmc))
        		.limit(5000)
        		.toList();
        
        var jmcStudentsAvgCourseCompletionPercent=
        		studentsList.stream()
        		.mapToDouble(s -> s.getPercentComplete("JMC"))
        		.average()
        		.getAsDouble();
        
        var javaStudentsCount = 
        		studentsList.stream()
        		.filter(s -> s.getEngagementMap().get("JMC")!= null)
        		.count()
        		;
        
        var jmcStudentsAvgCourseCompletionPercent2=
        		studentsList.stream()
        		.mapToDouble(s -> s.getPercentComplete("JMC"))
        		.reduce(0,(o,v) -> o+v);
        
        
        		
        System.out.println("Avg using average terminal operation = "+jmcStudentsAvgCourseCompletionPercent);
        System.out.println("Sum of course completion percentage using reduce = "+jmcStudentsAvgCourseCompletionPercent2);
        System.out.println("Avg of course completion percentage with reduce result = "+jmcStudentsAvgCourseCompletionPercent2/ javaStudentsCount);
        
        int eligibleCompletionPercent = (int) (jmcStudentsAvgCourseCompletionPercent * 1.25);
        
        System.out.println("eligibleCompletionPercent = "+eligibleCompletionPercent);
        Comparator<Student> longTermStudent = Comparator.comparing(Student::getYearEnrolled);
        
        List<Student> hardWorkers =  studentsList.stream()        		
        		.filter(s -> s.getPercentComplete("JMC") >= eligibleCompletionPercent)
        		.sorted(longTermStudent)
        		.limit(10)
        		.toList();
        
        
        hardWorkers.forEach(s -> System.out.print(s.getStudentId()+" "));
        
        Comparator<Student> uniqueComparator = longTermStudent.thenComparing(Student::getStudentId);
        //print same out as above 
        System.out.println();
        
        studentsList.stream()        		
        		.filter(s -> s.getPercentComplete("JMC") >= eligibleCompletionPercent)
        		.sorted(longTermStudent)
        		.limit(10)
        		//.toList()
        		//.collect(Collectors.toList())
        		//.collect(Collectors.toSet())
        		.collect(() -> new TreeSet<>(uniqueComparator), TreeSet::add, TreeSet::addAll)
        		.forEach(s -> {
        			s.addCourse(cgj);
        			System.out.print(s.getStudentId()+" ");
        			//System.out.println(s);
        			})
        		;
	}
}
