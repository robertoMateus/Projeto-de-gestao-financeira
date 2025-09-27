import style from './InputText.module.css'

interface InputTextProps {
    label?: string
}

const InputText = ({label}:  InputTextProps) => {
  return (
    <div>
        <div>
    {label &&( 
        <label>{label}</label>)}
        </div>

        <input className={style.input} type="text" />
   </div>
  )
}

export default InputText