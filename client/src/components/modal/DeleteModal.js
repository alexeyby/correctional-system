import { forwardRef } from 'react';
import styles from './DeleteModal.module.css';

const DeleteModal = forwardRef((props, ref) => {
    return (
        <div ref={ref} className='modal' id='deleteModal' tabIndex='-1'>
            <div className={'modal-dialog modal-dialog-centered ' + styles.modal_dialog}>
                <div className='modal-content'>
                    <div className={'modal-body ' + styles.modal_body}>
                        Вы действительно хотите продолжить?
                    </div>
                    <div className={'modal-footer ' + styles.modal_footer}>
                        <button className={'btn ' + styles.btn__accept + ' ' + styles.btn__modal} 
                            onClick={() => props.onAccept()}>Да</button>
                        <button className={'btn ' + styles.btn__cancel + ' ' + styles.btn__modal} 
                            onClick={() => props.onCancel()}>Нет</button>
                    </div>
                </div>
            </div>
        </div>
    );
});

export default DeleteModal;