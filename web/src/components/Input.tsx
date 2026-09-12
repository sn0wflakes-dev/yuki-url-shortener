import React, { useId, type ReactNode } from "react";

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  rightElement?: ReactNode;
}

export const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ label, error, rightElement, disabled, className = "", ...props }, ref) => {
    const generatedId = useId();
    const inputId = props.id || generatedId;

    const borderInput = error
      ? "border-red-500 focus:border-red-500 focus:ring-red-500/20"
      : "border-transparent focus:border-ctp-yellow-500 focus:ring-ctp-yellow-500/20";

    const bgClass = disabled
      ? "bg-ctp-surface1 text-ctp-text"
      : "bg-ctp-surface1 text-ctp-text shadow-sm transition duration-200 ease-in-out focus:outline-none focus:ring-4";

    return (
      <div className={`flex flex-col gap-1.5 font-sans ${className}`}>
        {label && (
          <label
            htmlFor={inputId}
            className={`text-md font-normal select-none ${disabled ? "text-ctp-subtext0" : "text-ctp-text"}`}
          >
            {label}
          </label>
        )}

        <div className="flex w-full items-center rounded-md bg-ctp-surface1">
          <input
            {...props}
            ref={ref}
            id={inputId}
            disabled={disabled}
            className={`w-full px-3.5 py-2.5 rounded-sm text-sm ${borderInput} ${bgClass} ${className}`}
          />

          {rightElement && (
            <div className="shrink-0">
              {rightElement}
            </div>
          )}

          {error && (
            <p className="text-xs font-medium text-ctp-red-500 mt-0.5" role="alert">
              {error}
            </p>
          )}

        </div>
      </div>
    )
  }
)
