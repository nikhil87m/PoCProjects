package com.ubp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubp.bo.AddTrainingBo;
import com.ubp.bo.EmployeeBo;
import com.ubp.bo.EmployeeTrainingBo;
import com.ubp.bo.EmployeeTrainingMappingBo;
import com.ubp.bo.Response;
import com.ubp.bo.Training;
import com.ubp.bo.TrainingBo;
import com.ubp.bo.TrainingDetailsBO;
import com.ubp.dao.AddTrainingDetailsDao;
import com.ubp.dao.CrudDao;
import com.ubp.service.IEmployeeTrainingService;

@RestController
@EnableAutoConfiguration
@CrossOrigin
@RequestMapping("UBP")
//@SpringBootApplication(scanBasePackages={"com.ubp.dao"})
public class EmployeeTrainingController {
	
	@Autowired
	IEmployeeTrainingService employeeTraningService;
	
	@Autowired
	AddTrainingDetailsDao addTrainingDetailsDao; 
	/**
	 * Method to submit Training details 
	 * @param trainingBo
	 * @return
	 */
	
	 @PostMapping(value = "/addTrainingDetails",produces="application/json")
		public Response addTrainingDetails(@RequestBody AddTrainingBo trainingBo) {
			System.out.println("In addTrainingDetails:"+trainingBo.toString());
			
			try{
			addTrainingDetailsDao.insertTrainingMasterData(trainingBo);
			}catch(SQLException se){
				se.printStackTrace();
			}
			// Create Response Object
			Response response = new Response("Done", trainingBo);
			System.out.println("response"+response);
			return response;
		}
	 
	 /**
	  *Function to read the Training details from XLS file and insert it into DB
	  * @param file
	  * @return
	  */
	 
	@PostMapping (value="/uploadFile",consumes="multipart/form-data")
	public Response uploadFile(	@RequestParam("file") MultipartFile file) {
		System.out.println("in uploadFile::"+file);

		// check if file is provided
				if (file == null ){
					Response response = new Response("Invalid form data", null);
					response.setStatus("400");
					return response;
				}

				try {
					addTrainingDetailsDao.readFromExcelAndSavetoDB(file);
				} catch (Exception e) {
					e.printStackTrace();
					Response response = new Response("Can not read file", null);
					response.setStatus("500");
					return response;
				}

				Response response = new Response("Saved file into DB", null);
				response.setStatus("200");
				return response;

	}

	/**
	 * Method to fetch employee domain assigned based on employee id
	 * @param emplId
	 * @return
	 */
	
	@GetMapping("emplId/{emplId}")
	public ResponseEntity<EmployeeTrainingBo> getEmployeeTranings(@PathVariable("emplId") String emplId) {	
	
		EmployeeTrainingBo emplTraningBo = employeeTraningService.getTrainingDetailsForEmp(emplId);
		return new ResponseEntity<EmployeeTrainingBo>(emplTraningBo, HttpStatus.OK);
	}
	
	/**
	 * 
	 * Method to fetch employee assigned training details based on employee id and training type
	 * @param emplId
	 * @param trainingType
	 * @return
	 */

	@GetMapping("emplId/{emplId}/trainingType/{trainingType}")
	public ResponseEntity<List<EmployeeTrainingMappingBo>> getEmployeeTraningByTraningType(@PathVariable("emplId") String emplId,@PathVariable("trainingType") String trainingType) {	
	
		List<EmployeeTrainingMappingBo> listEmplTraningByTraningType = employeeTraningService.getAllEmployeeTrainingByTrainingType(emplId, trainingType);
		return new ResponseEntity<List<EmployeeTrainingMappingBo>>(listEmplTraningByTraningType, HttpStatus.OK);
	}
	
	/**
	 * Method to update employee training status and completion date
	 * @param emplTrainingBO
	 * @return
	 */
	
	@PutMapping("updateEmplTraning")
	//@RequestMapping(value="updateEmplTraning", method=RequestMethod.PUT)
	public @ResponseBody Response updateEmplTraning(@RequestBody EmployeeTrainingBo emplTrainingBO) {
		
		System.out.println("traning size :: "+ emplTrainingBO.getTraining().size());
		Response response=new Response();
		response.setStatus("failure");
		try {
			
			response.setData(emplTrainingBO);
			
			boolean res=employeeTraningService.updateTraining_Emp_Mapping(emplTrainingBO);
			if(res) {
				response.setStatus("Success");
				return response;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return response;
	}

	/**
	 * This method fetch all employee list available in system
	 * @return
	 */
	
	@RequestMapping(value = "/getEmployeeDetails")
	public Map<String, String> getEmployeeDetails() {
	System.out.println("hello started");
	List<EmployeeBo> employee_list = null;
	Map<String, String> pmpid_nameMap = null;
	
	try {
		employee_list = CrudDao.getEmpIdAndPMPID();
		System.out.println("employee sizeeee ::"+employee_list.size());
		
		pmpid_nameMap = new HashMap<String, String>();
		for (EmployeeBo employeeBo : employee_list) {
			pmpid_nameMap.put(employeeBo.getPmpid()+"_"+employeeBo.getEmpid(), employeeBo.getName());
			System.out.println("Employee name :: " +employeeBo.getName());
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return pmpid_nameMap;
}
	

	/**
	 * This method assigned training to employee
	 * 
	 * @param trainingDetails
	 * @return
	 * @throws IOException
	 */

 @RequestMapping(method=RequestMethod.POST,value="/submitTrainingDetails",produces = MediaType.APPLICATION_JSON_VALUE)
 public @ResponseBody Response submit_TrainingAgainstEmployee(@RequestBody TrainingDetailsBO trainingDetails) throws IOException {
	 Response response = new  Response("error", null);
 System.out.println("Hello da emp "+trainingDetails.getTraining().size());
 
 System.out.println(" trainingDetails.getEmp() "+trainingDetails.getEmp());
 System.out.println(" trainingDetails.getIsExternal() "+trainingDetails.getIsExternal());
 System.out.println(" trainingDetails.getIsRequired() "+trainingDetails.getIsRequired());
 System.out.println(" trainingDetails.getRemark() "+trainingDetails.getRemark());
 System.out.println(" trainingDetails.getStatus() "+trainingDetails.getStatus());
 System.out.println(" trainingDetails.getType() "+trainingDetails.getType());
 System.out.println(" trainingDetails.getTraining() "+trainingDetails.getTraining());
 System.out.println(" trainingDetails.getDate() "+trainingDetails.getExpectedCompletionDate());

 try {
	CrudDao.createTraining_Emp_Mapping(trainingDetails);
	response.setStatus("success");
	return response;
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	
}
//trainingDetails.set

  return response;
 }


 @PostMapping(value = "/save")
	public Response postCustomer(@RequestBody EmployeeBo emp) {
		//cust.add(customer);
		System.out.println("posttttt");
		// Create Response Object
		Response response = new Response("Done", emp);
		return response;
	}
 
 



// get the list of training based on type
@RequestMapping(value = "/trainingType/{trainingType}/{pmpid_empid}")
public Map<String, String> trainingList(@PathVariable(value= "trainingType") String trainingType,
										@PathVariable(value= "pmpid_empid") String pmpid_empid) {
System.out.println("trainingList method from controller:::"+trainingType+" pmpid_empid :: "+pmpid_empid);
List<TrainingBo> training_list = null;
Map<String, String> courseid_nameMap = null;

//try {
String[] pmp_emp = pmpid_empid.split("_");

		try {
			training_list = CrudDao.getTrainingListOnTrainingType(trainingType,pmp_emp[1]);
			System.out.println("employee sizeeee ::"+training_list.size());
			
			courseid_nameMap = new HashMap<String, String>();
			for (TrainingBo trainingBo : training_list) {
				courseid_nameMap.put(trainingBo.getTraining_Id(), trainingBo.getCourse_Name());
				System.out.println("Employee name :: " +trainingBo.getTraining_Id());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     //getEmpIdAndPMPID();
	
	
//} catch (Exception e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
return courseid_nameMap;
}

@GetMapping(value="/generateReport", produces="application/vnd.ms-excel")
public ResponseEntity<InputStreamResource> getExcelFile() {
	System.out.println("inside getExcelFile");

	String fileName = addTrainingDetailsDao.writeToXlsx();

	File file = new File(fileName);
	InputStreamResource resource=null;
	try{
     resource = new InputStreamResource(new FileInputStream(file));
	}catch(FileNotFoundException e){
		e.printStackTrace();
		System.out.println("File generation might have failed");
	}

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(MediaType.parseMediaType("application/excel"))
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
}


@RequestMapping(path="/fetchAllTraining", method=RequestMethod.GET)
public List<Training> fetchAllTraining(){
	return employeeTraningService.fetchAllTraining();
}
}
