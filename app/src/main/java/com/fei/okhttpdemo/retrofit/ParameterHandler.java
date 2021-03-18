package com.fei.okhttpdemo.retrofit;

/**
 * @ClassName: ParameterHandler
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 16:21
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 16:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ParameterHandler<T> {

    public void apply(RequestBuilder requestBuilder, T value);

    class Query<T> implements ParameterHandler<T> {

        String key;

        public Query(String key) {

            this.key = key;
        }

        @Override
        public void apply(RequestBuilder requestBuilder, T value) {
//            Gson gson = new Gson();
//            String s = gson.toJson(value, String.class);
            requestBuilder.addQueryName(key,value.toString());
        }
    }

    class FieldMap<T> implements ParameterHandler<T> {

        String key;

        public FieldMap(String key) {

            this.key = key;
        }

        @Override
        public void apply(RequestBuilder requestBuilder, T value) {

            requestBuilder.addQueryName(key,String.valueOf(value));
        }
    }

}
