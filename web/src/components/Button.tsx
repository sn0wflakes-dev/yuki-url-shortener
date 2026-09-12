import type { ButtonHTMLAttributes, PropsWithChildren } from "react";

type ButtonProps = PropsWithChildren<ButtonHTMLAttributes<HTMLButtonElement>>;

const Button = ({onClick, disabled, children, type="button", className = "", ...props}: ButtonProps) => {

  return (
    <button
    type={type}
    onClick={onClick}
    className={`custom-btn ${className}`}
    {...props}
    >
    {children}
    </button>
  );
};

export default Button;
