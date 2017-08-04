import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by malefikus on 03/08/17.
 * 安排房间
 */
class Room {
    static boolean Conflict (ArrayList<ArrayList<Object>> Group1,
                             ArrayList<ArrayList<Object>> Group2){
        //flag为false表示没有冲突
        boolean flag = false;
        for (ArrayList<Object> aGroup1 : Group1){
            for (ArrayList<Object> aGroup2 : Group2){
                if (aGroup1.get(0) == aGroup2.get(0)){
                    //有冲突
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    static ArrayList<ArrayList<ArrayList<ArrayList<Object>>>> assignRoom(){
        ArrayList<ArrayList<ArrayList<ArrayList<Object>>>> assignRoom = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Object>>> Original = Merge.mergeGroup();
        int roomNum = 2;

        for (ArrayList<ArrayList<Object>> aOriginal : Original){
            //第一次迭代，新建第一个房间，将第一个组加进去
            if (assignRoom.size() == 0){
                ArrayList<ArrayList<ArrayList<Object>>> Room = new ArrayList<>();
                Room.add(aOriginal);
                assignRoom.add(Room);
                continue;
            }

            //是否加入前面房间的标志
            boolean addprev = false;
            for (ArrayList<ArrayList<ArrayList<Object>>> room : assignRoom){
//                ArrayList<ArrayList<ArrayList<Object>>> room = assignRoom.get(i);
                //如果前面的房间有空
                if (room.size() > 0 && room.size() < roomNum){
                    //且教师不冲突
                    boolean conflict = false;
                    for (ArrayList<ArrayList<Object>> aroom : room){
                        if (Conflict(aroom, aOriginal)){
                            conflict = true;
                            break;
                        }
                    }
                    //加入前面的房间
                    if (!conflict){
                        room.add(aOriginal);
                        addprev = true;
                        break;
                    }
                }
            }
            //否则新建房间加入
            if (!addprev){
                ArrayList<ArrayList<ArrayList<Object>>> newroom = new ArrayList<>();
                newroom.add(aOriginal);
                assignRoom.add(newroom);
            }
        }
        return assignRoom;
    }
}