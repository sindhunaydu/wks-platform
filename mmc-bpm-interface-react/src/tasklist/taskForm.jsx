import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import CloseIcon from '@mui/icons-material/Close';
import Slide from '@mui/material/Slide';
import { TransitionProps } from '@mui/material/transitions';
import FormControl from '@mui/material/FormControl';
import Input from '@mui/material/Input';
import FormHelperText from '@mui/material/FormHelperText';

const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export const TaskForm = ({ open, handleClickOpen, handleClose, components, task }) => {

    const [formComponents, setFormComponents] = useState([]);
    useEffect(() => {
        if (!task) {
            setFormComponents(components);
        } else if (task) {
            fetch('http://localhost:8081/form/' + task.id)
                .then((response) => response.json())
                .then((data) => {
                    setFormComponents(data.components);
                })
                .catch((err) => {
                    console.log(err.message);
                });
        }
    }, [task, components]);

    return (
        <div>
            <Dialog
                fullScreen
                open={open}
                onClose={handleClose}
                TransitionComponent={Transition}
            >
                <AppBar sx={{ position: 'relative' }}>
                    <Toolbar>
                        <IconButton
                            edge="start"
                            color="inherit"
                            onClick={handleClose}
                            aria-label="close"
                        >
                            <CloseIcon />
                        </IconButton>
                        <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
                            <div>{task?.name}</div>
                        </Typography>
                        <Button autoFocus color="inherit" onClick={handleClose}>
                            Claim
                        </Button>
                        <Button autoFocus color="inherit" onClick={handleClose}>
                            Complete
                        </Button>
                    </Toolbar>
                </AppBar>
                <div style={{display: 'grid', padding: '10px'}}>
                    {formComponents?.map(component => {
                        if (component.type === 'text') {
                            return (
                                <h2 key={component.id} id={component.id}>{component.text}</h2>
                            );
                        } else {
                            return (
                                <FormControl key={component.id} style={{padding: '5px'}}>
                                    <Input id={component.id} aria-describedby="my-helper-text" />
                                    <FormHelperText id="my-helper-text">{component.label}</FormHelperText>
                                </FormControl>
                            );
                        }
                    })}
                </div>
            </Dialog>
        </div>
    );
}