import {
  Dialog, DialogActions, DialogContent, DialogTitle,
  DialogContentText
} from "@mui/material";
import Button from '@mui/material/Button';

const OperationStatusDialog = ({ dialogOpen, handleClose, status, responseCode,
  onClick }) => {

  return (
    <>
      <Dialog open={dialogOpen} onClose={handleClose}>
        <DialogTitle>Operation Status</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {status}
            <br />
            {responseCode !== null ? (
              <>
                Response Code: {responseCode}
              </>
            ) : null}
          </DialogContentText>
          <DialogActions>
            <Button onClick={onClick} color="inherit">
              OK
            </Button>
          </DialogActions>
        </DialogContent>
      </Dialog>
    </>
  )
}

export default OperationStatusDialog;
