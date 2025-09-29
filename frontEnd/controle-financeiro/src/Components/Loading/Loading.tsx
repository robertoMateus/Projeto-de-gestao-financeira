import styles from "./Loading.module.css";
import { AiOutlineLoading3Quarters } from "react-icons/ai";

interface LoadingProps {
  visible: boolean;
}

const Loading = ({ visible }: LoadingProps) => {
  if (!visible) return null;

  return (
    <div className={styles.overlay}>
      <div className={styles.loaderContainer}>
        <span className={styles.iconWrapper}>
          <AiOutlineLoading3Quarters size={50} />
        </span>
        <p className={styles.text}>Carregando...</p>
      </div>
    </div>
  );
};

export default Loading;
