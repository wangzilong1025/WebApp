package com.sandi.web.common.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by liuqin on 2016/8/28.
 */
public class Table2BeanUtil {

    private static List<String> constraintsKey = new ArrayList<String>();
    private static boolean hasDate = false;

    public static void create(List tab_columns, String tableName, String path, List<String> key) throws Exception {
        //生成文件
        createFile(tableName, path);

        constraintsKey = key;
        //写入
        writeEntity(tableName, path, tab_columns);
        writeDao(tableName, path);
        writeSV(tableName, path);
        writeSVImpl(tableName, path);
    }

    /*
   * 生成文件夹和文件
   * path格式 E:\ESOP\IDEA\esop-app\esop-core-parent\esop-core\src\main\java\com\asiainfo\esop\sh\aaa 注意转义！
   * */
    public static void createFile(String tableName, String path) {
        String f = changeName(tableName).toString();

        // Entity
        File folder1 = new File(path + "\\entity");
        if (!folder1.exists()) {
            folder1.mkdirs();
        }
        String fileName1 = f + "Entity.java";
        File file1 = new File(path + "\\entity\\" + fileName1);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Dao
        File folder2 = new File(path + "\\dao");
        if (!folder2.exists()) {
            folder2.mkdirs();
        }
        String fileName2 = "I" + f + "Dao.java";
        File file2 = new File(path + "\\dao\\" + fileName2);
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //SV
        File folder3 = new File(path + "\\service\\interfaces");
        if (!folder3.exists()) {
            folder3.mkdirs();
        }
        String fileName3 = "I" + f + "SV.java";
        File file3 = new File(path + "\\service\\interfaces\\" + fileName3);
        if (!file3.exists()) {
            try {
                file3.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //SVImpl
        File folder4 = new File(path + "\\service\\impl");
        if (!folder4.exists()) {
            folder4.mkdirs();
        }
        String fileName4 = f + "SVImpl.java";
        File file4 = new File(path + "\\service\\impl\\" + fileName4);
        if (!file4.exists()) {
            try {
                file4.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void writeEntity(String tableName, String path, List tab_columns) throws Exception {
        StringBuffer head = new StringBuffer();
        StringBuffer body = new StringBuffer();

        /*----------------------------------------正文body-----------------------------------------------*/
        body.append("public class " + changeName(tableName) + "Entity extends BaseEntity{" + "\n");
        StringBuffer variables = new StringBuffer();
        StringBuffer constructor = new StringBuffer();
        for (int i = 0; i < tab_columns.size(); i++) {
            Map tmp = (Map) tab_columns.get(i);

            String columnName = tmp.get("COLUMN_NAME").toString();
            String dataType = tmp.get("DATA_TYPE").toString();
            String dataLength = tmp.get("DATA_LENGTH").toString();
            String dataPrecision = tmp.get("DATA_PRECISION").toString();
            String comments = tmp.get("COMMENTS").toString();

            Map map = createBody(columnName, dataType, dataLength, dataPrecision, comments);
            variables.append(map.get("variable"));
            constructor.append(map.get("constructor"));
        }
        body.append(variables);
        body.append(constructor);
        body.append("}");
        /*----------------------------------------头文件head-----------------------------------------------*/
        int index = path.indexOf("com\\");
        String relativePath = path.substring(index < 0 ? 0 : index, path.length());
        //引用包名
        head.append("package " + relativePath.replace("\\", ".").replace("/", ".") + ".entity;\n\n");
        //引用本地包
        head.append("import com.sandi.web.common.persistence.entity.BaseEntity;\n");
        if (constraintsKey.size() > 0) {
            head.append("import com.sandi.web.common.persistence.annotation.Id;");
        }
        head.append("\n\n");
        //引用java包
        if (hasDate == true) {
            head.append("import java.util.Date;");
            head.append("\n\n");
        }
        /*----------------------------------------写入-----------------------------------------------*/
        write(head.toString() + "" + body.toString(),
                path + "\\entity\\" + changeName(tableName).toString() + "Entity.java");
    }

    public static void writeDao(String tableName, String path) throws Exception {
        StringBuffer head = new StringBuffer();
        StringBuffer body = new StringBuffer();

        /*----------------------------------------正文body-----------------------------------------------*/
        body.append("public interface I" + changeName(tableName) + "Dao extends CrudDao<" + changeName(tableName)
                + "Entity,Long>{" + "\n" + "}");
        /*----------------------------------------头文件head-----------------------------------------------*/
        int index = path.indexOf("com\\");
        String relativePath = path.substring(index < 0 ? 0 : index, path.length());
        // 引用包名
        head.append("package " + relativePath.replace("\\", ".").replace("/", ".") + ".dao;\n\n");
        // 引用本地包
        head.append("import com.sandi.web.common.persistence.annotation.Dao;\n");
        head.append("import com.sandi.web.common.persistence.dao.CrudDao;\n");
        head.append("import " + relativePath.replace("\\", ".").replace("/", ".") + ".entity." + changeName(tableName) + "Entity;");
        head.append("\n\n");
        // @Dao注解
        head.append("@Dao(" + changeName(tableName) + "Entity.class)\n");
        /*----------------------------------------写入-----------------------------------------------*/
        write(head.toString() + "" + body.toString(),
                path + "\\dao\\I" + changeName(tableName).toString() + "Dao.java");
    }

    public static void writeSV(String tableName, String path) throws Exception {
        StringBuffer head = new StringBuffer();
        StringBuffer body = new StringBuffer();

         /*----------------------------------------正文body-----------------------------------------------*/
        body.append("public interface I" + changeName(tableName) + "SV extends CrudService{" + "\n" + "}");
        /*----------------------------------------头文件head-----------------------------------------------*/
        int index = path.indexOf("com\\");
        String relativePath = path.substring(index < 0 ? 0 : index, path.length());
        // 引用包名
        head.append("package " + relativePath.replace("\\", ".").replace("/", ".") + ".service.interfaces;\n\n");
        head.append("import com.sandi.web.common.persistence.service.CrudService;\n\n");
        /*----------------------------------------写入-----------------------------------------------*/
        write(head.toString() + "" + body.toString(),
                path + "\\service\\interfaces\\I" + changeName(tableName).toString() + "SV.java");
    }

    public static void writeSVImpl(String tableName, String path) throws Exception {
        StringBuffer head = new StringBuffer();
        StringBuffer body = new StringBuffer();

         /*----------------------------------------正文body-----------------------------------------------*/
        body.append("public class " + changeName(tableName) + "SVImpl extends CrudServiceImpl implements I" + changeName(tableName) + "SV{" + "\n");
        body.append("\t@Autowired\n");
        body.append("\tprivate I" + changeName(tableName) + "Dao dao;\n");
        body.append("\n");
        body.append("\tpublic CrudDao getDao() {\n\t\treturn dao;\n\t}\n");
        body.append("}");
        /*----------------------------------------头文件head-----------------------------------------------*/
        int index = path.indexOf("com\\");
        String relativePath = path.substring(index < 0 ? 0 : index, path.length());
        // 引用包名
        head.append("package " + relativePath.replace("\\", ".").replace("/", ".") + ".service.impl;\n\n");
        //引用本地包
        head.append("import com.sandi.web.common.persistence.dao.CrudDao;\n");
        head.append("import com.sandi.web.common.persistence.service.CrudServiceImpl;\n");
        head.append("import " + relativePath.replace("\\", ".").replace("/", ".") + ".dao.I" + changeName(tableName) + "Dao;\n");
        head.append("import " + relativePath.replace("\\", ".").replace("/", ".") + ".service.interfaces.I" + changeName(tableName) + "SV;\n");
        //引用框架包
        head.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        head.append("import org.springframework.stereotype.Service;\n");
        //head.append("import org.springframework.transaction.annotation.Transactional;\n\n");
        //@Service注解
        head.append("@Service\n");
        //head.append("@Transactional(readOnly = true)\n");
        /*----------------------------------------写入-----------------------------------------------*/
        write(head.toString() + "" + body.toString(),
                path + "\\service\\impl\\" + changeName(tableName).toString() + "SVImpl.java");
    }

    /*生成Entity变量，如：private Date createDate;
    * 根据变量生成其setter&getter构造器
    * */
    public static Map createBody(String columnName, String dataType, String dataLength, String dataPrecision, String comments) {
        Map map = new HashMap();
        //根据字段长度选择NUMBER对应的数据类型，暂不支持18位以上，默认为Long
        if (dataType.equals("NUMBER")) {
            if (Integer.parseInt(dataPrecision) > 0 && Integer.parseInt(dataPrecision) <= 9) {
                dataType = "Integer";
            } else if (Integer.parseInt(dataPrecision) > 9 && Integer.parseInt(dataPrecision) <= 18) {
                dataType = "Long";
            } else {
                dataType = "Long";
            }
        } else if (dataType.equals("VARCHAR2")) {
            dataType = "String";
        } else if (dataType.equals("DATE")) {
            hasDate = true;
            dataType = "Date";
        }else{
            dataType = "String";
        }

        StringBuffer variable = new StringBuffer();

        if (constraintsKey.size() > 0) {
            for (int i = 0; i < constraintsKey.size(); i++) {
                if (constraintsKey.get(i).equals(columnName)) {
                    variable.append("\t@Id\n");
                }
            }
        }

        variable.append("\tprivate ");
        variable.append(dataType + " ");
        String firstLower = changeName(columnName).substring(0, 1).toLowerCase() + changeName(columnName).substring(1);
        variable.append(firstLower + ";");
        variable.append("//" + comments.replaceAll("\n", "\t") + "\n\n");
        map.put("variable", variable);

        StringBuffer constructor = new StringBuffer(
                "\tpublic " + dataType + " get" + changeName(columnName) + "() {return " + firstLower + ";}\n\n");
        constructor.append("\tpublic void set" + changeName(columnName) + "(" + dataType + " " + firstLower + ") {this."
                + firstLower + " = " + firstLower + ";}\n\n");
        map.put("constructor", constructor);

        return map;
    }

    /*
    * 将字符串AAAA_BBBB_CCCC转成AaaaBbbbCccc
    * 若字符串为AAAA_BBBB_1则转成AaaaBbbb_1
    * */
    public static StringBuffer changeName(String columnName) {
        StringBuffer newName = new StringBuffer();
        String[] nameArr = columnName.split("_");
        for (int i = 0; i < nameArr.length; i++) {
            String lowerLetters = nameArr[i].toLowerCase();
            Pattern pattern = Pattern.compile("[0-9]*");
            if (pattern.matcher(nameArr[i]).matches()) {
                newName.append("_" + nameArr[i]);
                if (i != nameArr.length - 1 && !pattern.matcher(nameArr[i + 1]).matches()) {
                    newName.append("_");
                }
            } else {
                String firstLetterUp = lowerLetters.replaceFirst(lowerLetters.substring(0, 1),
                        lowerLetters.substring(0, 1).toUpperCase());
                newName.append(firstLetterUp);
            }
        }
        return newName;
    }

    /*
    * 执行写入
    * */
    public static void write(String txt, String path) throws Exception {
        File f = new File(path);
        FileOutputStream fo = new FileOutputStream(f);
        OutputStreamWriter o = new OutputStreamWriter(fo, "utf-8");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(o);
            bw.write(txt);
        } finally {
            bw.close();
        }
    }
}
