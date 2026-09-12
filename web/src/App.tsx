import './index.css';
import Button from "./components/Button.tsx";
import { Input } from './components/Input.tsx';
import { useState } from 'react';
import { shortenUrl } from './api/services/shorten-url-service.ts';

function App() {
  const [longUrl, setLongUrl] = useState('');
  const [alias, setAlias] = useState('');
  const [result, setResult] = useState('');
  const [loading, setLoading] = useState(false);

  const handleFormSubmit = async (event: React.SubmitEvent<HTMLFormElement>) => {
    event.preventDefault();

    setLoading(true);
    setResult('');

    try {
      const response = await shortenUrl({
        longUrl: longUrl,
        alias: alias
      });

      setResult(response.data.url);
    } catch (error) {
      console.error("Failed to fetch data");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <div className="flex flex-col justify-center-safe items-center h-screen w-full bg-ctp-crust">
        <div className='w-[70%] md:w-[70%] lg:w-[45%]'>

          <div id="text-header">
            <h1 className='text-3xl text-ctp-yellow-500 font-semibold text-center'>Yuki URL Shortener</h1>
            <h3 className='text-xl text-ctp-subtext1 text-center mt-4'>Make Your Loooong URL More Readable and Easy to Remember.</h3>
          </div>

          <form
            id='url-text-form'
            onSubmit={handleFormSubmit}
            className='px-6 py-6 mt-8 bg-ctp-surface0 gap-2.5 rounded-md w-full'>
            <div>
              <div className='flex flex-col gap-2.5 md:flex-row'>

                <Input
                  label='Long URL'
                  type='url'
                  name='longUrl'
                  value={longUrl}
                  onChange={(event) => setLongUrl(event.target.value)}
                  placeholder='https://example.com/very/long/url'
                  className='min-w-0 flex-7'
                ></Input>

                <Input
                  label='Alias (Optional)'
                  type='url'
                  name='alias'
                  value={alias}
                  onChange={(event) => setAlias(event.target.value)}
                  placeholder='/alias'
                  className='min-w-0 flex-3'
                ></Input>

              </div>
              {result && (
                <div className='mt-4'>
                  <Input
                    label='Result'
                    type='url'
                    name='result'
                    disabled={true}
                    value={"Test 123"}
                    placeholder=""
                    className='min-w-0 flex-3'
                    rightElement={
                      <Button
                        type='button'
                        onClick={() => navigator.clipboard.writeText(result)}
                        className='px-3.5 text-sm font-semibold text-ctp-blue-500 hover:text-ctp-blue-800'>Copy Link</Button>
                    }
                  ></Input>
                </div>
              )}
            </div>

            <Button
              type='submit'
              disabled={loading}
              className='mt-8 px-2 rounded-sm py-2 w-full font-bold'
            >{loading ? "Shortening..." : "Shorten Link"}</Button>

          </form>
        </div>
      </div>
    </>
  )
}

export default App
