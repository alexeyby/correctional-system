import { ClipLoader } from 'react-spinners';

function Loading() {
    const loadingSpinnerStyle = 'border-width: 10px';

    return (
        <div className='h-100 d-flex justify-content-center align-items-center'>
            <ClipLoader color='#cfa7ef' size={400} speedMultiplier={0.32} css={loadingSpinnerStyle} />
        </div>
    );
}

export default Loading;