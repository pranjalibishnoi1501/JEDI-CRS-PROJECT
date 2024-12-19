package com.flipkart.service;

import java.util.List;

// import org.apache.log4j.Logger;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.dao.AdminDaoInterface;
import com.flipkart.dao.AdminDaoOperation;
import com.flipkart.exception.CourseFoundException;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.ProfessorNotAddedException;
import com.flipkart.exception.StudentNotFoundException;
import com.flipkart.validator.AdminValidator;

/**
 * 
 * @author JDI-03
 * Implementations of Admin Operations
 * 
 */
public class AdminOperation implements AdminInterface{

	// private static Logger logger = Logger.getLogger(AdminOperation.class);
	private static volatile AdminOperation instance = null;
	
	private AdminOperation()
	{
		
	}
	
	/**
	 * Method to make AdminOperation Singleton
	 * @return
	 */
	public static AdminOperation getInstance()
	{
		if(instance == null)
		{
			synchronized(AdminOperation.class){
				instance = new AdminOperation();
			}
		}
		return instance;
	}
	

	AdminDaoInterface adminDaoOperation =AdminDaoOperation.getInstance();
	
	/**
	 * Method to Delete Course from Course Catalog
	 * @param courseCode
	 * @param courseList : Courses available in the catalog
	 * @throws CourseNotFoundException 
	 */
	@Override
	public void deleteCourse(String dropCourseCode, List<Course> courseList) throws CourseNotFoundException {
		if(!AdminValidator.isValidDropCourse(dropCourseCode, courseList)) {
			System.out.println("courseCode: " + dropCourseCode + " not present in catalog!");
			throw new CourseNotFoundException(dropCourseCode);
		}
		try {
			adminDaoOperation.deleteCourse(dropCourseCode);
		}
		catch(CourseNotFoundException e) {
			throw e;
		}
	}

	/**
	 * Method to add Course to Course Catalog
	 * @param course : Course object storing details of a course
	 * @param courseList : Courses available in catalog
	 * @throws CourseFoundException
	 */
	@Override
	public void addCourse(Course newCourse, List<Course> courseList) throws CourseFoundException {
		
		if(!AdminValidator.isValidNewCourse(newCourse, courseList)) {
			System.out.println("courseCode: " + newCourse.getCourseCode() + " already present in catalog!");
			throw new CourseFoundException(newCourse.getCourseCode());
		}
		try {
			adminDaoOperation.addCourse(newCourse);
		}
		catch(CourseFoundException e) {
			throw e;
		}
	}

	/**
	 * Method to view Students yet to be approved by Admin
	 * @return List of Students with pending admissions
	 */
	@Override
	public List<Student> viewPendingAdmissions() {
		return adminDaoOperation.viewPendingAdmissions();
	}
	
	/**
	 * Method to approve a Student 
	 * @param studentId
	 * @throws StudentNotFoundException 
	 */
	@Override
	public void approveStudent(int studentId, List<Student> studentList) throws StudentNotFoundException {
		
		if(!AdminValidator.isValidUnapprovedStudent(studentId, studentList)) {
			System.out.println("studentId: " + studentId + " is already approvet/not-present!");
			throw new StudentNotFoundException(studentId);
		}
		try {
			adminDaoOperation.approveStudent(studentId);
		}
		catch(StudentNotFoundException e) {
			throw e;
		}
	}

	/**
	 * Method to add Professor to DB
	 * @param professor : Professor Object storing details of a professor
	 * @throws ProfessorNotAddedException
	 */
	@Override
	public void addProfessor(Professor professor) throws ProfessorNotAddedException {
		
		try {
			adminDaoOperation.addProfessor(professor);
		}
		catch(ProfessorNotAddedException e) {
			throw e;
		}
	}

	/**
	 * Method to assign Course to a Professor
	 * @param courseCode
	 * @throws CourseNotFoundException 
	 */
	@Override
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException{
		
		try {
			adminDaoOperation.assignCourse(courseCode, professorId);
		}
		catch(CourseNotFoundException e) {
			throw e;
		}
	}
	
	/**
	 * Method to get list of courses in catalog
	 * @param catalogId
	 * @return List of courses in catalog
	 */
	@Override
	public List<Course> viewCourses(int catalogId) {
		
		return adminDaoOperation.viewCourses(catalogId);
	}
}
