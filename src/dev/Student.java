package dev;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class Student {
    private static long lastStudentId = 1;
    private final static Random random = new Random();

    private final long studentId;
    private final String countryCode;
    private final int yearEnrolled;
    private final int ageEnrolled;
    private final String gender;
    private final boolean programmingExperience;
    private final List<Course> courseList;

    private final Map<String, CourseEngagement> engagementMap = new HashMap<>();

    public Student(String countryCode, int yearEnrolled, int ageEnrolled, String gender,
                   boolean programmingExperience, Course... courses) {
        studentId = lastStudentId++;
        this.countryCode = countryCode;
        this.yearEnrolled = yearEnrolled;
        this.ageEnrolled = ageEnrolled;
        this.gender = gender;
        this.programmingExperience = programmingExperience;
        this.courseList = new ArrayList(List.of(courses));
        for (Course course : courses) {
            addCourse(course, LocalDate.of(yearEnrolled, 1, 1));
        }
    }

    public void addCourse(Course newCourse) {
        addCourse(newCourse, LocalDate.now());
    }

    public void addCourse(Course newCourse, LocalDate enrollDate) {

        engagementMap.put(newCourse.courseCode(),
                new CourseEngagement(newCourse, enrollDate, "Enrollment"));
    }

    public long getStudentId() {
        return studentId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getYearEnrolled() {
        return yearEnrolled;
    }

    public int getAgeEnrolled() {
        return ageEnrolled;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getCourses(){
    	List<String> courseTitles = new ArrayList<>();
    	courseTitles = courseList.stream()
    			.map(s -> s.courseCode())
    			.toList();
    	return List.copyOf(courseTitles);
    }
    	
    public boolean hasProgrammingExperience() {
        return programmingExperience;
    }

    public Map<String, CourseEngagement> getEngagementMap() {
        return Map.copyOf(engagementMap);
    }
    

    public int getYearsSinceEnrolled() {
        return LocalDate.now().getYear() - yearEnrolled;
    }

    public int getAge() {
        return ageEnrolled + getYearsSinceEnrolled();
    }

    public int getMonthsSinceActive(String courseCode) {

        CourseEngagement info = engagementMap.get(courseCode);
        return info == null ? 0 : info.getMonthsSinceActive();
    }

    public int getMonthsSinceActive() {

        int inactiveMonths = (LocalDate.now().getYear() - 2014) * 12;
        for (String key : engagementMap.keySet()) {
            inactiveMonths = Math.min(inactiveMonths, getMonthsSinceActive(key));
        }
        return inactiveMonths;
    }

    public double getPercentComplete(String courseCode) {

        var info = engagementMap.get(courseCode);
        return (info == null) ? 0 : info.getPercentComplete();
    }

    public void watchLecture(String courseCode, int lectureNumber, int month, int year) {

        var activity = engagementMap.get(courseCode);
        if (activity != null) {
            activity.watchLecture(lectureNumber, LocalDate.of(year, month, 1));
        }
    }

    private static String getRandomVal(String... data) {
        return data[random.nextInt(data.length)];
    }

    public static Student getRandomStudent(Course... courses) {

        int maxYear = LocalDate.now().getYear() + 1;
        int numberOfCourses = random.nextInt(1,courses.length+1);
        Course[] enrolledCourse = new Course[numberOfCourses];
        if(numberOfCourses ==courses.length ) {
        	Arrays.setAll(enrolledCourse, i -> courses[i]);
        }else {
        	int indexCounter = 0;
        	List<Course> randomCourse = new ArrayList<>();
        	while(indexCounter<numberOfCourses) {
        		int randomPickCourseIndex = random.nextInt(0,4);
        		if(randomCourse.contains(courses[randomPickCourseIndex])) {
        			continue;
        		}
        		randomCourse.add(courses[randomPickCourseIndex]);
        		// 
        		indexCounter++;
        	}
        	Arrays.setAll(enrolledCourse, i -> randomCourse.get(i));
        }
        
        
        Student student = new Student(
                getRandomVal("AU", "CA", "CN", "GB", "IN", "UA", "US"),
                random.nextInt(maxYear - 4, maxYear),
                random.nextInt(18, 90),
                getRandomVal("M", "F", "U"),
                random.nextBoolean(),
                enrolledCourse);
        for (Course c : enrolledCourse) {
            int lecture = random.nextInt(0, c.lectureCount());
            int year = random.nextInt(student.getYearEnrolled(), maxYear);
            int month = random.nextInt(1, 13);
            if (year == (maxYear - 1)) {
                if (month > LocalDate.now().getMonthValue()) {
                    month = LocalDate.now().getMonthValue();
                }
            }
            student.watchLecture(c.courseCode(), lecture, month, year);
        }
        
        
        return student;
    }
    


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", countryCode='" + countryCode + '\'' +
                ", yearEnrolled=" + yearEnrolled +
                ", ageEnrolled=" + ageEnrolled +
                ", gender='" + gender + '\'' +
                ", programmingExperience=" + programmingExperience +
                ", engagementMap=" + engagementMap +
                '}';
    }
}
