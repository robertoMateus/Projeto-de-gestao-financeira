import style from './InputText.module.css'

interface InputTextProps {
    label?: string
    obrigatory?: boolean
    placeholder?: string
}

const InputText = ({label, obrigatory, placeholder}:  InputTextProps) => {
  return (
    <div>
        <div>
    {label &&( 
        <label>{label}</label>)}
        {obrigatory && (<span style={{color: 'red'}}>*</span>)}
        </div>

        <input className={style.input} type="text" placeholder={placeholder}/>
   </div>
  )
}

export default InputText