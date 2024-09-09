import {
  Dialog, DialogActions, DialogContent, DialogTitle,
  DialogContentText
} from "@mui/material";
import Button from '@mui/material/Button';

const ConfirmDeleteDialog = ({ dialogConfirmOpen, handleCloseConfirm, contextText,
  contextVar, handleDeleteConfirm, dialogDeleteOpen, handleCloseDelete, status }) => {

  return (
    <>
      <Dialog open={dialogConfirmOpen} onClose={handleCloseConfirm}>
        <DialogTitle>CONFIRM DELETE?</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {contextText} {contextVar}
          </DialogContentText>
          <DialogActions>
            <Button onClick={handleDeleteConfirm} color="error">
              DELETE
            </Button>
            <Button onClick={handleCloseConfirm} color="inherit">
              CANCEL
            </Button>
          </DialogActions>
        </DialogContent>
      </Dialog>

      <Dialog open={dialogDeleteOpen} onClose={handleCloseDelete}>
        <DialogTitle>DELETED</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {contextText} {contextVar}
            <br />
            Status: {status}
          </DialogContentText>
          <DialogActions>
            <Button onClick={handleCloseDelete} color="inherit">
              OK
            </Button>
          </DialogActions>
        </DialogContent>
      </Dialog>
    </>
  )

}

export default ConfirmDeleteDialog;
