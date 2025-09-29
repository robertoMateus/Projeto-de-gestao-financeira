import styles from "./Button.module.css"
interface ButtonProps {
    nomeBotao: string;
    onClick?: () => void;
}

const Button = ({nomeBotao, onClick}: ButtonProps) => {
  return (
    <div>
        <button className={styles.botao} onClick={onClick}>
            {nomeBotao}
        </button>
    </div>
  )
}

export default Button