import java.util.ArrayList;

/**
 * Created by malefikus on 01/08/17.
 * 将Assign中人数太少的组合并
 */
class Merge {

    //计算某个组的学生人数
    private static int stuNum (ArrayList<ArrayList<Object>> vect){
        int sum = 0;
        for (ArrayList<Object> aVect : vect) {
            sum += (int) aVect.get(1);
        }
        return sum;
    }

    //计算某个组带n个学生的老师人数
    private static int countNum(ArrayList<ArrayList<Object>> vect, int n){
        int num = 0;
        for (ArrayList<Object> aVect : vect){
            if ((int) aVect.get(1) == n){
                num++;
            }
        }
        return num;
    }

    //将两个组合并并加入merged。返回值为是否为合法合并
    private static boolean mergeTwo(ArrayList<ArrayList<Object>> groupOne,
                                    ArrayList<ArrayList<Object>> groupTwo,
                                    ArrayList<ArrayList<Object>> merged){
        boolean canMerge = false;
        //先把groupOne加入merged
        merged.addAll(groupOne);
        //把groupTwo合并进merged
        for (ArrayList<Object> agroupTwo : groupTwo){
            boolean merge = false;
            for (ArrayList<Object> amerged : merged){
                //如果教师名字重复，合并人数
                if (agroupTwo.get(0)==amerged.get(0)){
                    amerged.set(1, (int)amerged.get(1)+(int)agroupTwo.get(1));
                    merge = true;
                    break;
                }
            }
            //如果整个列表里都没有重复的，将此项加入列表尾部
            if (!merge){
                merged.add(agroupTwo);
            }
        }
        //至此已得到了一个完整的merged组，判断此数组非零人数是否大于5
        if (merged.size()-countNum(merged, 0)<=5){
            canMerge = true;
            //从merged中减去多余的0学生教师
            int mergedsize = merged.size();
            for (int i = 0; i < mergedsize-5; i++){
                for (ArrayList<Object> amerged : merged){
                    if ((int)amerged.get(1) == 0){
                        merged.remove(amerged);
                        break;
                    }
                }
//                System.out.println(merged);
            }
        }
        return canMerge;
    }

    //将一个组分成几个组加入总分组，并删除原分组
    private static void splitGroup(ArrayList<ArrayList<Object>> originalGroup,
                                   ArrayList<ArrayList<ArrayList<Object>>> undergroup){
        //将originalGroup从undergroup中删除
        undergroup.remove(originalGroup);
        for (ArrayList<Object> aoriginalGroup : originalGroup){
            if ((int)aoriginalGroup.get(1)>0){
                //新开一个全0数组
                ArrayList<ArrayList<Object>> splited = new ArrayList<>();
                splited.addAll(originalGroup);
                //将这一元素删去
                splited.remove(aoriginalGroup);
                //剩下的元素全设为0
                for (ArrayList<Object> asplited : splited){
                    asplited.set(1, 0);
                }
                //将这一元素重新加上
                splited.add(0, aoriginalGroup);
                //将这一数组加入undergroup里
                undergroup.add(splited);
            }
        }
    }

    //从Assign分好的组中抽取出学生数够8人的组
    private static void separateGroup(ArrayList<ArrayList<ArrayList<Object>>> rawgroup,
                                   ArrayList<ArrayList<ArrayList<Object>>> fullgroup,
                                   ArrayList<ArrayList<ArrayList<Object>>> undergroup){
        for (ArrayList<ArrayList<Object>> aRawgroup : rawgroup) {
            if (stuNum(aRawgroup) == 8) {
                fullgroup.add(aRawgroup);
            } else {
                undergroup.add(aRawgroup);
            }
        }
    }

    static ArrayList<ArrayList<ArrayList<Object>>> mergeGroup(){
        //传入Assign.java中分好的组
        ArrayList<ArrayList<ArrayList<Object>>> rawGroup = Assign.AssignNum();
        ArrayList<ArrayList<ArrayList<Object>>> fullGroup = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Object>>> underGroup = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Object>>> mergedGroup = new ArrayList<>();

        //将满的、不满的组分出来
        separateGroup(rawGroup, fullGroup, underGroup);

        mergedGroup.addAll(fullGroup);

        //underGroup中凡是小于等于4学生的组进一步细分
        ArrayList<ArrayList<ArrayList<Object>>> cpunderGroup = new ArrayList<>();
        cpunderGroup.addAll(underGroup);
        for (ArrayList<ArrayList<Object>> aunderGroup : cpunderGroup){
            if (stuNum(aunderGroup) <= 4){
                splitGroup(aunderGroup, underGroup);
            }
        }

        //循环判断能否合并。能合并，将merged加入mergedGroup并从underGroup中删除这两项
        // 循环走过一遍之后若undergroup不空，将剩余项全部加入mergedGroup
        for (int i = 7; i > 0; i--){
            for (int j = 8-i; j>0; j--){
                for (ArrayList<ArrayList<Object>> aunderGroup : underGroup){
                    //新建一个copiedUnderGroup，以免在两层循环时合并同一个数组
                    ArrayList<ArrayList<ArrayList<Object>>> copiedUnder = new ArrayList<>();
                    copiedUnder.addAll(underGroup);
                    copiedUnder.remove(aunderGroup);
                    for (ArrayList<ArrayList<Object>> acopiedUnder : copiedUnder){
                        if (stuNum(aunderGroup) == i && stuNum(acopiedUnder)==j){
                            ArrayList<ArrayList<Object>> merged = new ArrayList<>();
                            //如果可以合并
                            if (mergeTwo(aunderGroup, acopiedUnder, merged)){
                                //将merged加入mergedGroup
                                mergedGroup.add(merged);
                                //从underGroup中删除这两个合并过的数组
                                aunderGroup.clear();
                                acopiedUnder.clear();
                                break;
                            }
                        }
                    }
                }
            }
        }

        //再遍历underGroup，将不空的项加入mergedGroup
        for (ArrayList<ArrayList<Object>> aunderGroup : underGroup){
            if (stuNum(aunderGroup) > 0){
                mergedGroup.add(aunderGroup);
            }
        }

        return mergedGroup;
    }

}
