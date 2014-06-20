package estacioneAki.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import estacioneAki.servico.ConexaoServidor;

public class LoginActivity extends Activity{
	private EditText txtCPF_usuario;
	private EditText txtSenha;
	private Button btnLogin;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);        
        btnLogin = (Button) findViewById(R.id.btnLogin);        
        txtCPF_usuario = (EditText) findViewById(R.id.txtCPF_usuario);
        txtSenha= (EditText) findViewById(R.id.txtSenha);
                
        OnClickListener btnLoginClick =  new MyOnClickListener();       	
        btnLogin.setOnClickListener(btnLoginClick);        
	}
	
	public class MyOnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			ConexaoServidor conexao = new ConexaoServidor();
		       
			if(txtCPF_usuario.getText().toString().length()<1){
				Toast.makeText(getApplicationContext(), "Login inválido", Toast.LENGTH_SHORT).show();
			}
			else if(txtSenha.getText().toString().length() <1){
				Toast.makeText(getApplicationContext(), "Senha inválida", Toast.LENGTH_SHORT).show();
			}
			else{
				try{
					//autenticar usuario
					String respostaWBLoginMotorista = conexao.loginMotorista(txtCPF_usuario.getText().toString(), txtSenha.getText().toString());	
					String Autoriza = respostaWBLoginMotorista.substring(0,1);
					respostaWBLoginMotorista = respostaWBLoginMotorista.substring(2);
					Log.v("ANA","login autoriza"+Autoriza);  
					
					if(Autoriza.compareTo("1")==0){
						Log.v("ANA","login"+txtCPF_usuario.getText().toString());						
						Toast.makeText(getApplicationContext(), respostaWBLoginMotorista, Toast.LENGTH_LONG).show();
						Intent it = new Intent(LoginActivity.this,EstacioneAkiMap.class);
						it.putExtra("cpf_usuario",txtCPF_usuario.getText().toString());
						startActivity(it);
					}
					else{
						Toast.makeText(getApplicationContext(), respostaWBLoginMotorista, Toast.LENGTH_LONG).show();
					}
				}						
				catch (Exception e) {
						e.printStackTrace();
				}			
			}
		}
	}
}