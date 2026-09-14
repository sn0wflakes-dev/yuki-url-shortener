function NotFound() {
  return (
    <>
      <div className="flex flex-col w-full h-screen justify-center-safe items-center bg-ctp-base">
        <div>
          <h1 className="text-3xl text-ctp-yellow-500 font-semibold text-center">ERROR 404 - PAGE NOT FOUND</h1>
          <p className="text-xl text-ctp-subtext1 text-center mt-4">You’ve got the wrong address.</p>
        </div>
      </div>
    </>
  );
};

export default NotFound;
