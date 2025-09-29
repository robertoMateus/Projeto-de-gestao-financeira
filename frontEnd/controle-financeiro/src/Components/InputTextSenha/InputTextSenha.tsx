import { useState } from "react";
import styles from "./InputTextSenha.module.css";
import { AiFillEye, AiFillEyeInvisible } from "react-icons/ai"; 

interface InputTextSenhaProps {
  label?: string;
  obrigatory?: boolean;
  placeholder?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  value?: string;
}

const InputTextSenha = ({ label, obrigatory, placeholder, onChange, value }: InputTextSenhaProps) => {
  const [showSenha, setShowSenha] = useState(false);

  const toggleSenha = () => {
    setShowSenha(!showSenha);
  };

  return (
    <div className={styles.container}>
      <div className={styles.labelContainer}>
        {label && <label>{label}</label>}
        {obrigatory && <span className={styles.obrigatory}>*</span>}
      </div>

      <div className={styles.inputWrapper}>
        <input
          className={styles.input}
          type={showSenha ? "text" : "password"}
          placeholder={placeholder}
          onChange={onChange}
          value={value}
        />
        <button 
          type="button" 
          className={styles.toggleButton} 
          onClick={toggleSenha}
        >
          {showSenha ?  <AiFillEye /> : <AiFillEyeInvisible />}
        </button>
      </div>
    </div>
  );
};

export default InputTextSenha;
