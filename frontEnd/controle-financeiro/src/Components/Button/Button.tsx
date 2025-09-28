import styles from "./Button.module.css"
interface ButtonProps {
    nomeBotao: string
}

const Button = ({nomeBotao}: ButtonProps) => {
  return (
    <div>
        <button className={styles.botao}>
            {nomeBotao}
        </button>
    </div>
  )
}

export default Button