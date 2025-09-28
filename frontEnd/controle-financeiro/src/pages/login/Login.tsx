import InputText from "../../Components/InputText/InputText"
import styles from "./Login.module.css"
import iconeLogin from "../../assets/Icone tela login.png"
import Button from "../../Components/Button/Button"

const Login = () => {
    return (
        <div className={` d-flex justify-content-center align-items-center ${styles.principalLogin}`}>
            <div className={`${styles.form}`}>
                <img src={iconeLogin} alt="Icone financeiro" className={styles.iconeLogin} />
                <h1>Controle Financeiro</h1>
                <div className={styles.inputForm}>
                    <InputText label="Usuario" obrigatory placeholder="Digite seu usuário..." />
                </div>
                <div className={styles.inputForm}>
                    <InputText label="Senha" obrigatory placeholder="Digite sua senha..." />
                </div>
                <div>
                <Button nomeBotao="Login" />
                </div>
            </div>
        </div>
    )
}

export default Login