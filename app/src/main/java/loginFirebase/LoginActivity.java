package loginFirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.br.gestao_de_ensino.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.br.gestao_de_ensino.Activity.MainActivity;

public class LoginActivity extends AppCompatActivity {

    //Criar as variáveis
    private EditText edt_email;
    private EditText edt_password;
    private Button btn_login;
    private Button btn_cadastrar;
    private ProgressBar loginProgressBar;
    private CheckBox ckb_mostrar_senha;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Referênciar váriaveis com os componentes.
        mAuth = FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        ckb_mostrar_senha = findViewById(R.id.ckb_mostrar_senha);

        //Método BTN LOGIN
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Criado variáveis para verificar o retorno do botão
                String loginEmail = edt_email.getText().toString();
                String loginSenha = edt_password.getText().toString();

                // validar se o usuario não vai clicar no login sem digitar email e senha

                if (!loginEmail.isEmpty() && !loginSenha.isEmpty()) {
                    //apresenta o progress bar
                    loginProgressBar.setVisibility(View.VISIBLE);
                    //consulta se o usuário existe no firebase
                    mAuth.signInWithEmailAndPassword(loginEmail, loginSenha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //se login e senha estiverem corretos vai para tela principal
                                    if (task.isSuccessful()) {
                                        abrirTelaPrincipal();
                                        //caso login e senha errados envia messagens de erro para o usuário
                                    } else {
                                        String error = task.getException().getMessage();
                                        System.out.println(error);
                                        String forma_inv = String.format(getString(R.string.formato_invalido_v1));
                                            Snackbar.make(v,forma_inv,
                                         Snackbar.LENGTH_SHORT).show();

                                        loginProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }else {
                    String preencha_campos = String.format(getString(R.string.preencha_campos_v1));
                    Snackbar.make(v,
                            preencha_campos,
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuario.class);
                startActivity(intent);
                finish();
            }
        });
        //Definir se mostra ou não a senha
        ckb_mostrar_senha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    //metodo ir para tela principal
    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}