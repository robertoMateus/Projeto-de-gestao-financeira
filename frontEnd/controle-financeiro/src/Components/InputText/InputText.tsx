import styles from "./InputText.module.css"

interface InputTextProps {
  label?: string;
  obrigatory?: boolean;
  placeholder?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  value?: string;
}

const InputText = ({label, obrigatory, placeholder, onChange, value}: InputTextProps) => {
  return (
    <div className={styles.container}>
      <div className={styles.labelContainer}>
        {label && <label>{label}</label>}
        {obrigatory && <span className={styles.obrigatory}>*</span>}
      </div>
      <input className={styles.input} type="text" placeholder={placeholder} onChange={onChange} value={value}/>
    </div>
  )
}

export default InputText
