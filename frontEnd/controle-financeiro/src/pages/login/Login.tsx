import InputText from "../../Components/InputText/InputText"
import styles from "./Login.module.css"

const Login = () => {
    return (
        <div className={` d-flex justify-content-center align-items-center ${styles.principalLogin}`}>

                <div className={`${styles.form}`}>
                    <InputText label="Usuario"/>
                </div>
        </div>
    )
}

export default Login