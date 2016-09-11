package comfranklicm.github.openmind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.JsonParser;
import comfranklicm.github.openmind.utils.MD5;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/8/29
 */
public class RegisterFragment extends Fragment {
    View view;
    EditText userName;
    EditText passWord;
    EditText retypePassWord;
    EditText realName;
    Button registerButton;
    TextView userNameError;
    TextView passWordError;
    TextView retypePassWordError;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_layout, container,false);
        final Spinner s = (Spinner) view.findViewById(R.id.spinner);
        initLayout();
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(
                //getActivity().getBaseContext(), R.array.departments, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.add("Hint to be displayed");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_showed_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                /*if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }*/
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };
        adapter.setDropDownViewResource(R.layout.spinner_option_items);
        adapter.add("建筑学院");
        adapter.add("机械工程学院");
        adapter.add("能源与环境学院");
        adapter.add("信息科学与工程学院");
        adapter.add("土木工程学院");
        adapter.add("电子科学与工程学院");
        adapter.add("数学系");
        adapter.add("自动化学院");
        adapter.add("计算机科学与工程学院");
        adapter.add("物理系");
        adapter.add("生物科学与医学工程学院");
        adapter.add("材料科学与工程学院");
        adapter.add("人文学院");
        adapter.add("经济管理学院");
        adapter.add("电气工程学院");
        adapter.add("外国语学院");
        adapter.add("体育系");
        adapter.add("化学化工学院");
        adapter.add("交通学院");
        adapter.add("仪器科学与工程学院");
        adapter.add("艺术学院");
        adapter.add("法学院");
        adapter.add("医学院");
        adapter.add("公共卫生学院");
        adapter.add("吴健雄学院");
        adapter.add("海外教育学院");
        adapter.add("软件学院");
        adapter.add("微电子学院");
        adapter.add("马克思主义学院");
        adapter.add("生命科学研究院");
        adapter.add("院系选择");
        s.setAdapter(adapter);
        s.setSelection(30);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if (!isUserNameValid(userName.getText().toString()))
                  {
                      userNameError.setVisibility(View.VISIBLE);
                  }
                  if (!isPassWordValid(passWord.getText().toString()))
                  {
                      passWordError.setVisibility(View.VISIBLE);
                  }
                  if (!isRetypePasswordValid(passWord.getText().toString(),retypePassWord.getText().toString()))
                {
                    retypePassWordError.setVisibility(View.VISIBLE);
                }
                if (isUserNameValid(userName.getText().toString())&&isPassWordValid(passWord.getText().toString())&&isRetypePasswordValid(passWord.getText().toString(),retypePassWord.getText().toString()))
                {
                    HttpPostRunnable runnable=new HttpPostRunnable();
                    runnable.setActionId(1);
                    runnable.setUsername(userName.getText().toString());
                    runnable.setPassword(MD5.getMD5Str(passWord.getText().toString()));
                    runnable.setRealname(realName.getText().toString());
                    runnable.setDepartment((String) s.getSelectedItem());
                    Thread thread=new Thread(runnable);
                    thread.start();
                    try{
                        thread.join();
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    JsonParser.ParseJson(1,runnable.getStrResult());
                    if(User.getInstance().getRegisterResult().equals("true"))
                    {
                        MyActivity activity=(MyActivity)getActivity();
                        activity.transactiontoLogin();
                        Toast.makeText(getContext(),"注册成功,请登录",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"注册失败"+User.getInstance().getRegisterError(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }
    private void initLayout()
    {
        userName=(EditText)view.findViewById(R.id.editText);
        passWord=(EditText)view.findViewById(R.id.editText2);
        retypePassWord=(EditText)view.findViewById(R.id.editText3);
        realName=(EditText)view.findViewById(R.id.editText4);
        userNameError=(TextView)view.findViewById(R.id.username_error);
        userNameError.setVisibility(View.INVISIBLE);
        passWordError=(TextView)view.findViewById(R.id.password_error);
        passWordError.setVisibility(View.INVISIBLE);
        retypePassWordError=(TextView)view.findViewById(R.id.retry_password_error);
        retypePassWordError.setVisibility(View.INVISIBLE);
        registerButton=(Button)view.findViewById(R.id.register_button);
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    userNameError.setVisibility(View.INVISIBLE);
                }else {
                    if(!isUserNameValid(userName.getText().toString()))
                    {
                        userNameError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    passWordError.setVisibility(View.INVISIBLE);
                }
                else
                {
                    if (!isPassWordValid(passWord.getText().toString()))
                    {
                        passWordError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        retypePassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    retypePassWordError.setVisibility(View.INVISIBLE);
                }else
                {
                    if (!isRetypePasswordValid(passWord.getText().toString(),retypePassWord.getText().toString()))
                    {
                        retypePassWordError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    private boolean isUserNameValid(String username)
    {
       return username.matches("^[0-9a-zA-Z]{3,15}$");
    }
    private boolean isPassWordValid(String password)
    {
        return password.matches("^[0-9a-zA-Z]{6,16}$");
    }
    private boolean isRetypePasswordValid(String password,String retypepassword)
    {
        return password.equals(retypepassword);
    }
}
