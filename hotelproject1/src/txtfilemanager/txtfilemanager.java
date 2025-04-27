package txtfilemanager;	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

	public class txtfilemanager {  
			private String FileName;
			private int Row;
			//هروقت خواستم یه سطر رو بخونم اینو فراخوانی میکنیم
			
			public txtfilemanager(String FileName) {
			    this.FileName = FileName;
			    File file = new File(FileName);

			    try {
			        if (!file.exists()) {
			            file.createNewFile();  // اگه فایل نبود، بسازش
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			    }

			    Row = 0;
			}


			//یه متد برا یsearchRow
			public String searchRow(int RoomNum) {
			String D[] = getArrayFromFile(); // دریافت کل داده‌های فایل داخل آرایه

				if (RoomNum < 0 || RoomNum >= D.length) // بررسی اینکه شماره اتاق معتبر است یا نه
					  return "this room not found!";

					  return D[RoomNum]; // برگرداندن همان سطری که شماره اتاق دارد
				}
			
			////////////////////
			public void updateRow(int RoomNum, String newData) {
			    String[] D = getArrayFromFile(); // خواندن تمام داده‌های فایل

			    if (RoomNum < 0 || RoomNum >= D.length) { // بررسی اعتبار شماره سطر
			        System.out.println("This row not found!");
			        return;
			    }

			    D[RoomNum] = newData + "\n"; // اضافه کردن `\n` برای سازگاری با `getArrayFromFile`

			    writeArrayToFile(D); // نوشتن تغییرات در فایل
			}

			public void writeArrayToFile(String[] data) {
			    try {
			        // باز کردن فایل برای نوشتن
			        PrintWriter out = new PrintWriter(new FileWriter(this.FileName)); // استفاده از FileWriter برای نوشتن در فایل

			        // حلقه برای نوشتن هر عنصر از آرایه در یک خط جداگانه
			        for (int i = 0; i < data.length; i++) {
			            out.println(data[i]); // نوشتن هر عنصر در یک خط جداگانه
			        }


			        out.close(); // بستن فایل برای ذخیره‌سازی داده‌ها
			    } catch (Exception e) {
			        // مدیریت خطا در صورت وقوع
			        e.printStackTrace();
			    }
			}
			
			
			//متد برای اینکه اتاق خالی است یا نه ؟
			public void isRoomAvailable(int roomNumber) {
				//این بخش باید توی manager مربوط به خودش حل بشه
			}

			//متد برای شمارش تعداد کل اتاق ها خالی  یا مهمان ها 
			public void countRowsContaining(String keyword) {
				//توی managerباید کامل بشه 
			   			}
			
			//public void deleteRowByValue(String value)
			//متد برای حذف رزرو  یک اتاق
			
			
			
			//متد deletRow
			public void deletRow(int row) {
				if (row<=0 || row>Row)
					return ;
				
					String s[]=getArrayFromFile();		
					String news="";
					for ( int i=0;i<s.length;i++)
						if(i!=row-1)
							news+=s[i];
					setIntoFile(news);
			}
		
			//مند برای برگرداندن تعداد سطر ها 
			
			public int seleccount() {
				return Row;
			}
			private  void _UpdateRow() {
				int c=0;
				 try {

				        Scanner input = new Scanner(new File(FileName));
				        while (input.hasNextLine()) {
				        	input.nextLine();
				        	c++;
				        }
				        input.close();
				    } 
				    catch (FileNotFoundException e) {
				        e.printStackTrace();
				    }
				    Row= c;
				
				 
			 }
			public void AppendRow(String newRow) {
				String s=getFromFile();
				if (s=="")
				 s=newRow ;
				else
				 s+="\n"+newRow;
				 setIntoFile(s);
				}
			
			public void setIntoFile(String s) {
				
				try {
					PrintWriter out=new PrintWriter(this.FileName);
					out.println(s);
					out.close();
					_UpdateRow();
				}
				catch (Exception e) {
					// TODO: handle exception
				}}
				
			
			///برگرداندن ارایه
			public String[] getArrayFromFile() {
			    File file = new File(this.FileName);
			    List<String> lines = new ArrayList<>();  // استفاده از ArrayList برای ذخیره خطوط فایل

			    try {
			        Scanner input = new Scanner(file);
			        while (input.hasNextLine()) {
			            lines.add(input.nextLine());  // اضافه کردن هر خط به ArrayList
			        }
			        input.close();
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    }

			    // تبدیل ArrayList به آرایه و برگرداندن آن
			    return lines.toArray(new String[0]);  
			}

			
			private String getFromFile() {
			    File file = new File(this.FileName);
			    String s = "";

			    try {
			        Scanner input = new Scanner(file);
			        while (input.hasNextLine()) {
			            if (s.equals(""))  
			                s = input.nextLine();  
			            else  
			                s += "\n" + input.nextLine();  // اینجا تغییر داده شد
			        }
			        input.close();
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    }
			    return s;
			}

			
			
			//ایجاد یک کتد برای create
			public void CreateFile() {
				setIntoFile("");
				  Row=0;

			}
			//برای ایجاد متد clear
			public void Clear() {
				  CreateFile();
			}
				
			public void writeStringToFile(String data) {
			    try {
			        FileWriter fw = new FileWriter(FileName, false); // false = بازنویسی فایل
			        fw.write(data);
			        fw.close();
			    } catch (IOException e) {
			        System.out.println("خطا در نوشتن فایل: " + e.getMessage());
			    }
			}

	}  

