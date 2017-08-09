import java.io.File;
import java.util.*;

/**
 * Created by malefikus on 27/07/17.
 * 文件输入输出、创建老师-学生的map、根据后序规则确定每个老师在每组中带的学生人数
 */
class Fileio {

    // importing the excel file
    private static ArrayList<ArrayList<Object>> Import(){
        File file = new File("input.xls");
        ArrayList<ArrayList<Object>> result = ExcelUtil.readExcel(file);

        // deleting unnecessary information
        for (ArrayList<Object> aResult : result) {
            aResult.remove(2);
            aResult.remove(2);
//            for(int j = 0;j<result.get(i).size(); j++){
//                System.out.println("row "+i+" column "+j+": "+ result.get(i).get(j).toString());
//            }
        }

        return result;
    }

    // creating a public variable for imported data
    static ArrayList<ArrayList<Object>> output = Import();

    // grouping the students by teachers
    static Map<String, List<String>> Group(){
        Map<String, List<String>> groupMap = new HashMap<>();
        for (ArrayList<Object> anOutput : output) {
            String student = (String) anOutput.get(1);
            String teacher = (String) anOutput.get(2);
            if (groupMap.containsKey(teacher)) {
                groupMap.get(teacher).add(student);
            } else {
                List<String> stulist = new ArrayList<>();
                stulist.add(student);
                groupMap.put(teacher, stulist);
            }
        }
//        System.out.println(groupMap.toString());
        return groupMap;
    }

    // creating a public variable for grouped data
    static Map<String, List<String>> grouped = Group();

    // creating the vector for teachers
    static ArrayList<ArrayList<Object>> Num(){
        ArrayList<ArrayList<Object>> rowList = new ArrayList<>();
        ArrayList<Object> colList;

        for (String key : grouped.keySet()){
            colList = new ArrayList<>();
            colList.add(key);

            int divisor = 1;
            if (grouped.get(key).size()>12){
                divisor = 4;
            }
//            else if (grouped.get(key).size()>8){
//                divisor = 3;
//            }
            else if (grouped.get(key).size()>6){
                divisor = 2;
            }

            colList.add(grouped.get(key).size());   //number of students per teacher,总人数
            colList.add(grouped.get(key).size()/divisor);   //次数
//            colList.add((grouped.get(key).size()+divisor-1)/divisor);   //take the ceiling,次数
            colList.add(divisor);   //每组人数
            colList.add(grouped.get(key).size()%divisor);   //余数
            rowList.add(colList);
//            System.out.println(rowList.get(0).toString());
        }
        return rowList;
    }

    // exporting the data to an excel file
    static void Export(){
        ExcelUtil.writeExcel(output,"output.xls");
    }
}