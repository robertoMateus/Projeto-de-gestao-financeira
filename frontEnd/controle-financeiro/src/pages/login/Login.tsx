import InputText from "../../Components/InputText/InputText";
import InputTextSenha from "../../Components/InputTextSenha/InputTextSenha";
import Button from "../../Components/Button/Button";
import Alerta from "../../Components/Alerta/Alerta";
import { useState } from "react";
import axios from "axios";
import { Container, Row, Col } from "react-bootstrap";
import styles from "./Login.module.css";
import iconeLogin from "../../assets/Icone tela login.png";
import type { AlertType } from "../../Components/Alerta/Alerta";
import Loading from "../../Components/Loading/Loading";

const Login = () => {
  const [usuario, setUsuario] = useState<string>("");
  const [senha, setSenha] = useState<string>("");
  const [alerta, setAlerta] = useState<{ type: AlertType; message: string } | null>(null);
  const [loading, setLoading] = useState(false);

  function loginUsuario() {
  const dadosLogin = { usuario, senha };
  setLoading(true);

  axios
    .post("http://localhost:8080/usuarios/login", dadosLogin)
    .then(() => {
      setLoading(false);
    })
    .catch((error) => {
      setLoading(false);
      setAlerta({ type: "error", message: error.response.data });
    });
}

  return (
    <div className={styles.principalLogin}>
         <Loading visible={loading} />
      <Container className="h-100 d-flex justify-content-center align-items-center">
        <Row className="justify-content-center w-100">
          <Col xs={12} sm={10} md={8} lg={6} xl={5}>
            <div className={styles.form}>
              <img src={iconeLogin} alt="Ícone financeiro" className={styles.iconeLogin} />
              <h1>Controle Financeiro</h1>
              {alerta && <Alerta type={alerta.type} message={alerta.message} onClose={() => setAlerta(null)} />}
              
              <div className={styles.inputForm}>
                <InputText
                  label="Usuário"
                  obrigatory
                  placeholder="Digite seu usuário..."
                  onChange={(e) => setUsuario(e.target.value)}
                  value={usuario}
                />
              </div>

              <div className={styles.inputForm}>
                <InputTextSenha
                  label="Senha"
                  obrigatory
                  placeholder="Digite sua senha..."
                  onChange={(e) => setSenha(e.target.value)}
                  value={senha}
                />
              </div>

              <div className={styles.inputForm}>
                <Button nomeBotao="Login" onClick={loginUsuario} />
              </div>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Login;
