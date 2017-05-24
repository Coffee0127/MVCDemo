package tw.edu.fju.sample.utils;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import tw.edu.fju.sample.model.EmpJSON;
import tw.edu.fju.sample.model.EmpVO;

public final class GsonTypes {

    /**
     * Type of {@link List}<{@link EmpVO}>
     */
    public static final Type LIST_EMP_TYPE = new TypeToken<List<EmpVO>>() {}.getType();

    /**
     * Type of {@link List}<{@link EmpJSON}>
     */
    public static final Type LIST_EMP_JSON_TYPE = new TypeToken<List<EmpJSON>>() {}.getType();

    private GsonTypes() {
    }

}
