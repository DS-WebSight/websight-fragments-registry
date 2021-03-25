import React from 'react';
import RestClient from 'websight-rest-atlaskit-client/RestClient';

const getWebFragments = (fragmentsKey, fragmentsCallback, errorNotification) => {
    const restClient = new RestClient('websight-fragments-registry');
    restClient.get({
        action: 'get-web-fragments',
        parameters: { key: fragmentsKey },
        onSuccess: (data) => {
            const imports = data.entity.map(path => import(path));
            Promise.all(imports)
                .then(modules => fragmentsCallback(modules.map(module => module.default)))
                .catch(error => {
                    console.warn('Error during loading fragments', error);
                    errorNotification('Error during loading content');
                });
        },
        onError: data => errorNotification(data.message, data.messageDetails),
        onNonFrameworkError: data => errorNotification(data.message, data.messageDetails)
    });
}

class WebFragments extends React.Component {

    constructor(props) {
        super(props);

        const { fragmentsKey, loadingComponent, errorNotification, ...componentProps } = this.props;

        this.state = {
            components: null,
            fragmentsKey: fragmentsKey || console.warn('Fragments key is empty'),
            loadingComponent: loadingComponent,
            errorNotification: errorNotification || console.warn,
            componentProps: componentProps
        }
    }

    componentDidMount() {
        getWebFragments(
            this.state.fragmentsKey,
            (components) => this.setState({ components: components }),
            this.state.errorNotification);
    }

    render() {
        const { components, loadingComponent, componentProps } = this.state;
        if (components === null) {
            if (loadingComponent) {
                return loadingComponent;
            } else {
                return (<></>);
            }
        }
        return (components.map((Component, index) => (<Component key={index} {...componentProps} />)));
    }
}

export { getWebFragments };
export default WebFragments;
