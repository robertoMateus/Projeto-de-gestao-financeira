import  { useState, useEffect } from "react";
import styles from "./Alerta.module.css";
import { AiOutlineCheckCircle, AiOutlineCloseCircle, AiOutlineExclamationCircle } from "react-icons/ai";

export type AlertType = "error" | "warning" | "success";

interface AlertProps {
  type: AlertType;
  message: string;
  duration?: number; // opcional, sobrescreve duração padrão
  onClose?: () => void; // callback quando o alerta desaparecer
}

// Definir duração padrão fora do componente para evitar aviso do ESLint
const defaultDurations: Record<AlertType, number> = {
  error: 5000,
  warning: 4000,
  success: 3000,
};

const Alerta = ({ type, message, duration, onClose }: AlertProps) => {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (message) {
      setVisible(true);
      const timer = setTimeout(() => {
        setVisible(false);
        if (onClose) onClose();
      }, duration || defaultDurations[type]);

      return () => clearTimeout(timer);
    }
  }, [message, type, duration, onClose]); // ESLint não reclama mais

  const renderIcon = () => {
    const iconProps = { size: 24, color: type === "warning" ? "#000" : "#fff" };

    switch (type) {
      case "error":
        return (
          <span className={styles.icon}>
            <AiOutlineCloseCircle {...iconProps} />
          </span>
        );
      case "warning":
        return (
          <span className={styles.icon}>
            <AiOutlineExclamationCircle {...iconProps} />
          </span>
        );
      case "success":
        return (
          <span className={styles.icon}>
            <AiOutlineCheckCircle {...iconProps} />
          </span>
        );
      default:
        return null;
    }
  };

  return (
    <div
      className={`${styles.alertContainer} ${styles[type]} ${visible ? styles.show : ""}`}
    >
      {renderIcon()}
      <span>{message}</span>
    </div>
  );
};

export default Alerta;
