import type { ButtonHTMLAttributes, PropsWithChildren } from "react";

type ButtonProps = PropsWithChildren<ButtonHTMLAttributes<HTMLButtonElement>>;

const Button = ({onClick, disabled, children, type="button", className = "", ...props}: ButtonProps) => {

  const disableState = disabled 
    ? 'bg-ctp-yellow-200 text-ctp-subtext1' 
    : 'bg-ctp-yellow-500 text-ctp-crust hover:bg-ctp-yellow-600';

  return (
    <button
    type={type}
    onClick={onClick}
    className={`custom-btn ${disableState} ${className}`}
    {...props}
    >
    {children}
    </button>
  );
};

export default Button;
