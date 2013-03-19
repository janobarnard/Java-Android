import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ZaIDNumber {

	public ZaIDNumber() {
		
	}

	/*
	YYMMDDGSSSCAZ

	YYMMDD : Date of birth (DOB)
	     G : Gender. 0-4 Female; 5-9 Male
	   SSS : Sequence No. for DOB/G combination
	     C : Citizenship. 0 SA; 1 Other
	     A : Usually 8, or 9 but can be other values
	     Z : Calculated control (check) digit
 */
	
	public String getDateOfBirth(String idNumber){
			
		Calendar ci = Calendar.getInstance();
		int futureDate = ci.get(Calendar.YEAR);
		String year = String.valueOf(futureDate+2).substring(0, 2);
		int century = 0;
		
if (Integer.parseInt(idNumber.substring(0, 2))>Integer.parseInt(year)){
	century =19;
}
else{
	century =20;
}
if(isValidDate(idNumber.substring(0,6))==true)
		return century+idNumber.substring(0,2)+"-"+idNumber.substring(2,4)+"-"+idNumber.substring(4,6);
else
	return "Invalid";
		}
		
	public boolean isValidDate(String myDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		if (myDate.trim().length()!=dateFormat.toPattern().length())
			return false;
					
dateFormat.setLenient(false);

try{
	dateFormat.parse(myDate.trim());
}
catch (ParseException pa){
	return false;
}
return true;
		}
	
	public String getGender(String idNumber){
		 int value = Integer.parseInt(idNumber.substring(6, 7));

		 if (value>=0 & value<5)
		 {
			 return "Female";
		 }
		 else 
			 if(value>4 & value<=9)
			 {
				 return "Male";
			 }
			 else
				 return "Unable to determine";
	}
	
	public String getCitizenship(String idNumber){
		 int value = Integer.parseInt(idNumber.substring(10, 11));

		 if (value==0)
			 return "South African";

		 else if (value==1)
			 return "Immigrant";
		 else
				 return "Unable to determine";
	}
	
	public boolean validateId(String idNumber){
		
		// 1.)Add all the digits in the odd positions (excluding last digit)
		int oddDigitsTotal=Integer.parseInt(idNumber.substring(0, 1))+Integer.parseInt(idNumber.substring(2, 3))+Integer.parseInt(idNumber.substring(4, 5))+Integer.parseInt(idNumber.substring(6, 7))+Integer.parseInt(idNumber.substring(8, 9))+Integer.parseInt(idNumber.substring(10, 11));
		// 2.)Move the even positions into a field and multiply the number by 2
		String evenToMultiply=Integer.parseInt(idNumber.substring(1, 2))+""+Integer.parseInt(idNumber.substring(3, 4))+""+Integer.parseInt(idNumber.substring(5, 6))+""+Integer.parseInt(idNumber.substring(7, 8))+""+Integer.parseInt(idNumber.substring(9, 10))+""+Integer.parseInt(idNumber.substring(11, 12));
		int evenResult=Integer.parseInt(evenToMultiply)*2;
		// 3.)Add the digits of the result in [2]
		String evenResultStr = String.valueOf(evenResult);
		int digits = String.valueOf(evenResult).length();
		int evenTotal = 0;
		for (int i = 0; i < digits; i++){
			 evenTotal = evenTotal+Integer.parseInt(evenResultStr.substring(i,i+1));
		}
		// 4.)Add the answer in [3] to the answer in [1]
		String oddAndEvenTotal = String.valueOf(oddDigitsTotal + evenTotal);
		//5.)Subtract [4] (if [4]'s a two digit answer use the last digit)from 10
		int totalCheck=10;
		if (oddAndEvenTotal.length()==2 & Integer.parseInt(oddAndEvenTotal.substring(1))!=0){
		totalCheck = 10-Integer.parseInt(oddAndEvenTotal.substring(1));
		}
		else{
			totalCheck = 10-totalCheck;
		}
		
		// 6.) Compare calculated value against ID check digit
int checkDigit =Integer.parseInt(idNumber.substring(12));
		while (totalCheck==checkDigit)
		{
		return true;	
		}
			return false;
}
}
